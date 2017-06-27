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

package com.acmutv.socstream.common.source.meta.serial;

import com.acmutv.socstream.common.source.meta.Match;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

/**
 * The JSON serializer for {@link Match}.
 * @author Giacomo Marciani {@literal <gmarciani@acm.org>}
 * @since 1.0
 * @see Match
 */
public class MatchMetadataConfigurationSerializer extends StdSerializer<Match> {

  /**
   * The singleton of {@link MatchMetadataConfigurationSerializer}.
   */
  private static MatchMetadataConfigurationSerializer instance;

  /**
   * Returns the singleton of {@link MatchMetadataConfigurationSerializer}.
   *
   * @return the singleton.
   */
  public static MatchMetadataConfigurationSerializer getInstance() {
    if (instance == null) {
      instance = new MatchMetadataConfigurationSerializer();
    }
    return instance;
  }

  /**
   * Initializes the singleton of {@link MatchMetadataConfigurationSerializer}.
   */
  private MatchMetadataConfigurationSerializer() {
    super((Class<Match>) null);
  }

  @Override
  public void serialize(Match value, JsonGenerator gen, SerializerProvider provider) throws IOException {
    gen.writeStartObject();

    /*

    gen.writeStringField("source", value.getSource().getName());
    gen.writeStringField("kafka.topic", value.getTopic());
    gen.writeStringField("kafka.zookeeper", value.getKafkaProperties().getProperty(KafkaProperties.ZOOKEEPER_CONNECT));
    gen.writeStringField("kafka.bootstrap", value.getKafkaProperties().getProperty(KafkaProperties.BOOTSTRAP_SERVERS));
    gen.writeStringField("kafka.group", value.getKafkaProperties().getProperty(KafkaProperties.GROUP_ID));
    gen.writeStringField("dataset", value.getDataset());
    gen.writeStringField("hidden.metric", value.getHiddenMetric().getName());
    gen.writeNumberField("hidden.locality", value.getHiddenLocality());
    gen.writeArrayFieldStart("hidden.weights");
    for (Double w : value.getHiddenWeights()) gen.writeNumber(w);
    gen.writeEndArray();
    gen.writeNumberField("hidden.threshold", value.getHiddenThreshold());
    gen.writeStringField("potential.metric", value.getPotentialMetric().getName());
    gen.writeNumberField("potential.locality", value.getPotentialLocality());
    gen.writeArrayFieldStart("potential.weights");
    for (Double w : value.getPotentialWeights()) gen.writeNumber(w);
    gen.writeEndArray();
    gen.writeNumberField("potential.threshold", value.getPotentialThreshold());
    gen.writeNumberField("ewma.factor", value.getEwmaFactor());
    gen.writeStringField("neo4j.hostname", value.getNeo4jConfig().getHostname());
    gen.writeStringField("neo4j.username", value.getNeo4jConfig().getUsername());
    gen.writeStringField("neo4j.password", value.getNeo4jConfig().getPassword());
    gen.writeNumberField("parallelism", value.getParallelism());
    */

    gen.writeEndObject();
  }
}