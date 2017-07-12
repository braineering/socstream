/*
  The MIT License (MIT)

  Copyright (c) 2017 Giacomo Marciani and Michele Porretta

  Permission is hereby granted, free of charge, to any person obtaining a copy
  of this software and associated documentation files (the "Software"), to deal
  in the Software without restriction, including without limitation the rights
  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
  copies of the Software, and to permit persons to whom the Software is
  furnished to do so, subject to the following conditions:


  The above copyright notice and this permission notice shall be included in
  all copies or substantial portions of the Software.


  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.  IN NO EVENT SHALL THE
  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
  THE SOFTWARE.
 */
package com.acmutv.socstream.common.source.kafka.schema;

import com.acmutv.socstream.common.tuple.PositionSensorEvent;
import com.acmutv.socstream.common.tuple.RichSensorEvent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.flink.streaming.util.serialization.AbstractDeserializationSchema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

/**
 * The Kafka deserialization schema for {@link PositionSensorEvent}.
 *
 * @author Giacomo Marciani {@literal <gmarciani@acm.org>}
 * @since 1.0
 */
@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class PositionSensorEventDeserializationSchema extends AbstractDeserializationSchema<PositionSensorEvent> {

  /**
   * The logger.
   */
  private static final Logger LOG = LoggerFactory.getLogger(PositionSensorEventDeserializationSchema.class);

  /**
   * The special tuple signaling the end of stream.
   */
  private static final PositionSensorEvent END_OF_STREAM = new PositionSensorEvent(0, Long.MAX_VALUE, 0, 0, 0);

  /**
   * The starting timestamp (events before this will be ignored).
   */
  private Long tsStart;

  /**
   * The ending timestamp (events after this will be ignored).
   */
  private Long tsEnd;

  /**
   * The starting timestamp to ignore (events between this and {@code tsEndIgnore} will be ignored).
   */
  private Long tsStartIgnore;

  /**
   * The ending timestamp to ignore (events between {@code tsStartIgnore} and this will be ignored).
   */
  private Long tsEndIgnore;

  /**
   * The ignore list for sensors id.
   */
  private Set<Long> ignoredSensors;

  /**
   * The map (SID)->(PID).
   */
  private Map<Long,Long> sid2Pid;

  /**
   * The tuple signaling the end of stream.
   */
  private PositionSensorEvent eos;

  /**
   * Creates a new deserialization schema.
   */
  public PositionSensorEventDeserializationSchema() {
    super();
  }

  /**
   * De-serializes the byte message.
   *
   * @param message The message, as a byte array.
   * @return The de-serialized message as an object.
   */
  @Override
  public PositionSensorEvent deserialize(byte[] message) throws IOException {
    PositionSensorEvent event;

    final String strEvent = new String(message);

    try {
      event = PositionSensorEvent.valueOf(strEvent);
    } catch (IllegalArgumentException exc) {
      LOG.warn("Malformed sensor event: {}", strEvent);
      return null;
    }

    if (this.ignoredSensors.contains(event.getId())) {
      LOG.info("Ignored sensor event (untracked SID): {}", strEvent);
      return null;
    }

    final long ts = event.getTs();

    if (ts < this.tsStart) {
      LOG.info("Ignored sensor event (before match start): {}", strEvent);
      return null;
    } else if (ts > tsStartIgnore && ts < tsEndIgnore) {
      LOG.info("Ignored sensor event (within match interval): {}", strEvent);
      return null;
    } else if (ts > this.tsEnd) {
      LOG.info("Ignored sensor event (after match end): {}", strEvent);
      LOG.info("Emitting EOS tuple: {}", this.eos);
      return eos;
    }

    event.setId(this.sid2Pid.get(event.getId()));

    return event;
  }

  /**
   * Checks if the end of stream has been reached.
   * @param event the current event.
   * @return true, if the end of stream has been reached; false, otherwise.
   */
  @Override
  public boolean isEndOfStream(PositionSensorEvent event) {
    /*
    final boolean isEnd = event.getTs() > this.getTsEnd();
    if (isEnd) {
      LOG.info("End of stream reached.");
    }
    return isEnd;
    */
    return false;
  }
}
