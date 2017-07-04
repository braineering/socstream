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
 * The Kafka deserialization schema for {@link RichSensorEvent}.
 *
 * @author Giacomo Marciani {@literal <gmarciani@acm.org>}
 * @since 1.0
 */
@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class RichSensorEventDeserializationSchema extends AbstractDeserializationSchema<RichSensorEvent> {

  /**
   * The logger.
   */
  private static final Logger LOG = LoggerFactory.getLogger(RichSensorEventDeserializationSchema.class);

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
   * Creates a new deserialization schema.
   */
  public RichSensorEventDeserializationSchema() {
    super();
  }

  /**
   * De-serializes the byte message.
   *
   * @param message The message, as a byte array.
   * @return The de-serialized message as an object.
   */
  @Override
  public RichSensorEvent deserialize(byte[] message) throws IOException {
    RichSensorEvent event = null;

    final String strEvent = new String(message);

    try {
      event = RichSensorEvent.valueOf(strEvent);
      final long ts = event.getTs();
      if (ts < this.tsStart || ts > this.tsEnd || (ts > tsStartIgnore && ts < tsEndIgnore) ||
          this.ignoredSensors.contains(event.getId())) {
        LOG.warn("Ignored sensor event: {}", strEvent);
        return null;
      }
      event.setId(this.sid2Pid.get(event.getId()));
    } catch (IllegalArgumentException exc) {
      LOG.warn("Malformed sensor event: {}", strEvent);
    }

    return event;
  }
}