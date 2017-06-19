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

package com.acmutv.socstream.config.serial;

import com.acmutv.socstream.config.AppConfiguration;
import com.acmutv.socstream.common.source.KafkaProperties;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

/**
 * The JSON serializer for {@link AppConfiguration}.
 * @author Giacomo Marciani {@literal <gmarciani@acm.org>}
 * @since 1.0
 * @see AppConfiguration
 * @see AppConfigurationDeserializer
 */
public class AppConfigurationSerializer extends StdSerializer<AppConfiguration> {

  /**
   * The singleton of {@link AppConfigurationSerializer}.
   */
  private static AppConfigurationSerializer instance;

  /**
   * Returns the singleton of {@link AppConfigurationSerializer}.
   *
   * @return the singleton.
   */
  public static AppConfigurationSerializer getInstance() {
    if (instance == null) {
      instance = new AppConfigurationSerializer();
    }
    return instance;
  }

  /**
   * Initializes the singleton of {@link AppConfigurationSerializer}.
   */
  private AppConfigurationSerializer() {
    super((Class<AppConfiguration>) null);
  }

  @Override
  public void serialize(AppConfiguration value, JsonGenerator gen, SerializerProvider provider) throws IOException {
    gen.writeStartObject();

    gen.writeStringField("source", value.getSource().getName());
    gen.writeStringField("kafka.topic", value.getTopic());
    gen.writeStringField("kafka.zookeeper", value.getKafkaProperties().getProperty(KafkaProperties.ZOOKEEPER_CONNECT));
    gen.writeStringField("kafka.bootstrap", value.getKafkaProperties().getProperty(KafkaProperties.BOOTSTRAP_SERVERS));
    gen.writeStringField("kafka.group", value.getKafkaProperties().getProperty(KafkaProperties.GROUP_ID));
    gen.writeStringField("dataset", value.getDataset());
    gen.writeNumberField("parallelism", value.getParallelism());

    gen.writeEndObject();
  }
}