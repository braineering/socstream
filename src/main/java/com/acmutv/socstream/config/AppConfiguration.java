/*
  The MIT License (MIT)

  Copyright (c) 2016 Giacomo Marciani and Michele Porretta

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

package com.acmutv.socstream.config;

import com.acmutv.socstream.common.db.DbConfiguration;
import com.acmutv.socstream.common.source.KafkaProperties;
import com.acmutv.socstream.common.source.SourceType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.Yaml;

import java.util.ArrayList;
import java.util.List;

/**
 * The app configuration model.
 * @author Giacomo Marciani {@literal <gmarciani@acm.org>}
 * @author Michele Porretta {@literal <mporretta@acm.org>}
 * @since 1.0
 * @see Yaml
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppConfiguration {

  private static final Logger LOGGER = LoggerFactory.getLogger(AppConfiguration.class);

  /**
   * Default value for property {@code source}.
   */
  public static final SourceType SOURCE = SourceType.FILE;

  /**
   * Default value for property {@code topic}.
   */
  public static final String TOPIC = "main-topic";

  /**
   * Default value for property {@code kafkaProperties}.
   */
  public static final KafkaProperties KAFKA_PROPERTIES = new KafkaProperties();

  /**
   * Default value for property {@code dataset}.
   */
  public static final String DATASET = "resources/socstream/data/socstream.data";

  /**
   * Default value for property {@code parallelism}.
   */
  public static final int PARALLELISM = 2;

  /**
   * The source type.
   * Default is: {@code FILE}.
   */
  private SourceType source = SOURCE;
  /**
   * The topic to subscribe to.
   * Default is: {@code socstream}.
   */
  private String topic = TOPIC;

  /**
   * Kafka connector properties.
   * Default is {@code {}}.
   */
  private KafkaProperties kafkaProperties = new KafkaProperties(KAFKA_PROPERTIES);

  /**
   * The pathname of the file or directory containing the dataset.
   * Default is: {@code socstream-dataset.txt}
   */
  private String dataset = DATASET;

  /**
   * The operator parallelism.
   * Default is: {@code 2}.
   */
  private int parallelism = PARALLELISM;

  /**
   * Constructs a configuration as a copy of the one specified.
   * @param other the configuration to copy.
   */
  public AppConfiguration(AppConfiguration other) {
    this.copy(other);
  }

  /**
   * Copies the settings of the configuration specified.
   * @param other the configuration to copy.
   */
  public void copy(AppConfiguration other) {
    this.source = other.source;
    this.topic = other.topic;
    this.kafkaProperties = other.kafkaProperties;
    this.dataset = other.dataset;
    this.parallelism = other.parallelism;
  }

  /**
   * Restores the default configuration settings.
   */
  public void toDefault() {
    this.source = SOURCE;
    this.topic = TOPIC;
    this.kafkaProperties = KAFKA_PROPERTIES;
    this.dataset = DATASET;
    this.parallelism = PARALLELISM;
  }

}
