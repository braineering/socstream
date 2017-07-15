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
import com.acmutv.socstream.common.tuple.RichSensorEvent2;
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
@EqualsAndHashCode(callSuper = false)
public class RichSensorEventDeserializationSchema2 extends AbstractDeserializationSchema<RichSensorEvent2> {

  /**
   * The logger.
   */
  private static final Logger LOG = LoggerFactory.getLogger(RichSensorEventDeserializationSchema2.class);

  /**
   * The ending timestamp (events after this will be ignored).
   */
  private long tsEnd;

  /**
   * The tuple signaling the end of stream.
   */
  private RichSensorEvent2 eos;

  /**
   * Creates a new deserialization schema.
   */
  public RichSensorEventDeserializationSchema2() {
    super();
  }

  /**
   * De-serializes the byte message.
   *
   * @param message The message, as a byte array.
   * @return The de-serialized message as an object.
   */
  @Override
  public RichSensorEvent2 deserialize(byte[] message) throws IOException {
    RichSensorEvent2 event;

    final String strEvent = new String(message);

    try {
      event = RichSensorEvent2.valueOf(strEvent);
    } catch (IllegalArgumentException exc) {
      //LOG.warn("Malformed sensor event: {}", strEvent);
      return null;
    }

    if (event.getTs() > this.getTsEnd()) {
      return this.eos;
    }

    //LOG.info("Emitting event: {}", event);

    return event;
  }

  /**
   * Checks if the end of stream has been reached.
   * @param event the current event.
   * @return true, if the end of stream has been reached; false, otherwise.
   */
  @Override
  public boolean isEndOfStream(RichSensorEvent2 event) {
    return (event != null) && event.getTs() > this.getTsEnd();
  }
}