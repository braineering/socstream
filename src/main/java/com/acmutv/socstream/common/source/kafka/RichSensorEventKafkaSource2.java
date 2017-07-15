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

import com.acmutv.socstream.common.source.kafka.schema.RichSensorEventDeserializationSchema;
import com.acmutv.socstream.common.source.kafka.schema.RichSensorEventDeserializationSchema2;
import com.acmutv.socstream.common.tuple.RichSensorEvent;
import com.acmutv.socstream.common.tuple.RichSensorEvent2;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer010;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * A source that produces {@link RichSensorEvent} from a Kafka topic.
 *
 * @author Giacomo Marciani {@literal <gmarciani@acm.org>}
 * @since 1.0
 */
public class RichSensorEventKafkaSource2 extends FlinkKafkaConsumer010<RichSensorEvent2> {

  /**
   * The logger.
   */
  private static final Logger LOG = LoggerFactory.getLogger(RichSensorEventKafkaSource2.class);

  /**
   * Constructs a new Kafka source for sensor events with ignoring features.
   *
   * @param topic Kafka topics.
   * @param props Kafka properties.
   * @param tsEnd the ending timestamp (events after this will be ignored).
   */
  public RichSensorEventKafkaSource2(String topic, Properties props,
                                     long tsEnd) {
    super(topic, new RichSensorEventDeserializationSchema2(
        tsEnd), props);
  }
}
