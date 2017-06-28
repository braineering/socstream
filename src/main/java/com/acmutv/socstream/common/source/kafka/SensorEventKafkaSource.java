/*
  The MIT License (MIT)

  Copyright (c) 2017 Giacomo Marciani

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
package com.acmutv.socstream.common.source.kafka;

import com.acmutv.socstream.common.tuple.SensorEvent;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer010;
import org.apache.flink.streaming.util.serialization.AbstractDeserializationSchema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Properties;
import java.util.Set;

/**
 * A source that produces {@link SensorEvent} from a Kafka topic.
 *
 * @author Giacomo Marciani {@literal <gmarciani@acm.org>}
 * @since 1.0
 */
public class SensorEventKafkaSource extends FlinkKafkaConsumer010<SensorEvent> {

  /**
   * The logger.
   */
  private static final Logger LOG = LoggerFactory.getLogger(SensorEventKafkaSource.class);

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
   * Constructs a new Kafka source for sensor events.
   *
   * @param topic Kafka topics.
   * @param props Kafka properties.
   */
  public SensorEventKafkaSource(String topic, Properties props) {
    super(topic, new SensorEventDeserializationSchema(), props);
  }

  /**
   * Constructs a new Kafka source for sensor events with ignoring features.
   *
   * @param topic Kafka topics.
   * @param props Kafka properties.
   * @param tsStart the starting timestamp (events before this will be ignored).
   * @param tsEnd the ending timestamp (events after this will be ignored).
   * @param tsStartIgnore the starting timestamp to ignore (events between this and {@code tsEndIgnore} will be ignored).
   * @param tsEndIgnore the ending timestamp to ignore (events between {@code tsStartIgnore} and this will be ignored).
   * @param ignoredSensors the list of sensors id to be ignored.
   */
  public SensorEventKafkaSource(String topic, Properties props,
                                long tsStart, long tsEnd,
                                long tsStartIgnore, long tsEndIgnore,
                                Set<Long> ignoredSensors) {
    super(topic, new SensorEventDeserializationSchema(), props);
    this.tsStart = tsStart;
    this.tsEnd = tsEnd;
    this.tsStartIgnore = tsStartIgnore;
    this.tsEndIgnore = tsEndIgnore;
    this.ignoredSensors = ignoredSensors;
  }

  /**
   * The Kafka deserialization schema for {@link SensorEvent}.
   *
   * @author Giacomo Marciani {@literal <gmarciani@acm.org>}
   * @since 1.0
   */
  public static final class SensorEventDeserializationSchema extends AbstractDeserializationSchema<SensorEvent> {

    /**
     * The logger.
     */
    private static final Logger LOG = LoggerFactory.getLogger(SensorEventDeserializationSchema.class);

    /**
     * De-serializes the byte message.
     *
     * @param message The message, as a byte array.
     * @return The de-serialized message as an object.
     */
    @Override
    public SensorEvent deserialize(byte[] message) throws IOException {
      SensorEvent sensorEvent = null;

      try {
        sensorEvent = SensorEvent.valueOf(new String(message));
      } catch (IllegalArgumentException exc) {
        LOG.warn("Malformed sensor event: {}", message);
      }

      return sensorEvent;
    }
  }
}
