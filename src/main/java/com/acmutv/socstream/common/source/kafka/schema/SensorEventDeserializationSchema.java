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
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.flink.streaming.util.serialization.AbstractDeserializationSchema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Map;

/**
 * The Kafka deserialization schema for {@link RichSensorEvent}.
 *
 * @author Giacomo Marciani {@literal <gmarciani@acm.org>}
 * @since 1.0
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class SensorEventDeserializationSchema extends AbstractDeserializationSchema<RichSensorEvent> {

  /**
   * The logger.
   */
  private static final Logger LOG = LoggerFactory.getLogger(SensorEventDeserializationSchema.class);

  /**
   * The map (SID)->(PID).
   */
  private Map<String,String> sid2Pid;

  /**
   * Creates a new deserialization schema.
   * @param sid2Pid the map (SID)->(PID).
   */
  public SensorEventDeserializationSchema(Map<String,String> sid2Pid) {
    super();
    this.sid2Pid = sid2Pid;
  }

  /**
   * Creates a new deserialization schema.
   */
  public SensorEventDeserializationSchema() {
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

    try {
      event = RichSensorEvent.valueOf(new String(message));
      event.setId(this.sid2Pid.get(event.getId()));
    } catch (IllegalArgumentException exc) {
      LOG.warn("Malformed sensor event: {}", message);
    }

    return event;
  }
}