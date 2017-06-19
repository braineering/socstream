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

package com.acmutv.socstream.ui;

import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

/**
 * This class realizes the command line interface options of the whole application.
 * The class is implemented as a singleton.
 * @author Giacomo Marciani {@literal <gmarciani@acm.org>}
 * @author Michele Porretta {@literal <mporretta@acm.org>}
 * @since 1.0
 * @see Option
 */
public class BaseOptions extends Options {

  private static final long serialVersionUID = 1L;

  /**
   * The CLI description for the option `version`.
   */
  private static final String DESCRIPTION_VERSION = "Show app version.";

  /**
   * The CLI description for the option `help`.
   */
  private static final String DESCRIPTION_HELP = "Show app helper.";

  /**
   * The CLI description for the option `config`.
   */
  private static final String DESCRIPTION_CONFIG = "Custom configuration.";

  /**
   * The CLI description for the option `source`.
   */
  private static final String DESCRIPTION_SOURCE = "The data source type (FILE|KAFKA).";

  /**
   * The CLI description for the option `dataset`.
   */
  private static final String DESCRIPTION_DATASET = "The path of the dataset.";

  /**
   * The CLI description for the option `kafkaTopic`.
   */
  private static final String DESCRIPTION_KAFKA_TOPIC = "The Kafka topic.";

  /**
   * The CLI description for the option `kafkaBootstrap`.
   */
  private static final String DESCRIPTION_KAFKA_BOOTSTRAP = "The Kafka bootstrap servers.";

  /**
   * The CLI description for the option `kafkaZookeper`.
   */
  private static final String DESCRIPTION_KAFKA_ZOOKEPER = "The Kafka Zookeper connect.";

  /**
   * The CLI description for the option `kafkaGroup`.
   */
  private static final String DESCRIPTION_KAFKA_GROUP = "The Kafka group id.";

  /**
   * The CLI description for the option `output`.
   */
  private static final String DESCRIPTION_OUTPUT = "The path of the output file.";

  /**
   * The CLI description for the option `hiddenMetric`.
   */
  private static final String DESCRIPTION_HIDDEN_METRIC = "The metric used for link detection. It must be one of: LOCAL, QUASI_LOCAL, WEIGHTED_QUASI_LOCAL.";

  /**
   * The CLI description for the option `hiddenLocality`.
   */
  private static final String DESCRIPTION_HIDDEN_LOCALITY = "The locality degree for link detection. The locality degree must be a strictly positive integer.";

  /**
   * The CLI description for the option `hiddenWeights`.
   */
  private static final String DESCRIPTION_HIDDEN_WEIGHTS = "The weight vector for weighted quasi-local link detection. The sum of weights must be equal to 1.0.";

  /**
   * The CLI description for the option `hiddenThreshold`.
   */
  private static final String DESCRIPTION_HIDDEN_THRESHOLD = "The threshold for link detection. The threshold must be in (0,1).";

  /**
   * The CLI description for the option `potentialMetric`.
   */
  private static final String DESCRIPTION_POTENTIAL_METRIC = "The metric used for link prediction. It must be one of: LOCAL, QUASI_LOCAL, WEIGHTED_QUASI_LOCAL.";

  /**
   * The CLI description for the option `potentialLocality`.
   */
  private static final String DESCRIPTION_POTENTIAL_LOCALITY = "The locality degree for link prediction. The locality degree must be a strictly positive integer.";

  /**
   * The CLI description for the option `potentialWeights`.
   */
  private static final String DESCRIPTION_POTENTIAL_WEIGHTS = "The weight vector for weighted quasi-local link prediction. The sum of weights must be equal to 1.0.";

  /**
   * The CLI description for the option `potentialThreshold`.
   */
  private static final String DESCRIPTION_POTENTIAL_THRESHOLD = "The threshold for link prediction. The threshold must be in (0,1).";

  /**
   * The CLI description for the option `ewmaFactor`.
   */
  private static final String DESCRIPTION_EWMA_FACTOR = "The EWMA factor for recent observations.";
  /**
   * The CLI description for the option `neo4jHostname`.
   */
  private static final String DESCRIPTION_NEO4J_HOSTNAME = "The Neo4J hostname.";

  /**
   * The CLI description for the option `neo4jUsername`.
   */
  private static final String DESCRIPTION_NEO4J_USERNAME = "The Neo4J username.";

  /**
   * The CLI description for the option `neo4jPassword`.
   */
  private static final String DESCRIPTION_NEO4J_PASSWORD = "The Neo4J password.";

  /**
   * The CLI description for the option `parallelism`.
   */
  private static final String DESCRIPTION_PARALLELISM = "The parallelism of topology operators.";

  /**
   * The singleton instance of {@link BaseOptions}.
   */
  private static BaseOptions instance;

  /**
   * Returns the singleton instance of {@link BaseOptions}.
   * @return the singleton.
   */
  public static BaseOptions getInstance() {
    if (instance == null) {
      instance = new BaseOptions();
    }
    return instance;
  }

  /**
   * Constructs the singleton of {@link BaseOptions}.
   */
  private BaseOptions() {
    Option version = this.optVersion();
    Option help = this.optHelp();
    Option config = this.optConfig();
    Option source = this.optSource();
    Option dataset = this.optDataset();
    Option kafkaTopic = this.optKafkaTopic();
    Option kafkaBootstrap = this.optKafkaBootstrap();
    Option kafkaZookeper = this.optKafkaZookeper();
    Option kafkaGroup = this.optKafkaGroup();
    Option output = this.optOutput();
    Option hiddenMetric = this.optHiddenMetric();
    Option hiddenLocality = this.optHiddenLocality();
    Option hiddenWeights = this.optHiddenWeights();
    Option hiddenThreshold = this.optHiddenThreshold();
    Option potentialMetric = this.optPotentialMetric();
    Option potentialLocality = this.optPotentialLocality();
    Option potentialWeight = this.optPotentialWeights();
    Option potentialThreshold = this.optPotentialThreshold();
    Option ewmaFactor = this.optEwmaFactor();
    Option parallelism = this.optParallelism();

    Option neo4jHostname = this.optNeo4jHostname();
    Option neo4jUsername = this.optNeo4JUsername();
    Option neo4jPassword = this.optNeo4JPassword();

    super.addOption(version);
    super.addOption(help);
    super.addOption(config);
    super.addOption(source);
    super.addOption(dataset);
    super.addOption(kafkaTopic);
    super.addOption(kafkaBootstrap);
    super.addOption(kafkaZookeper);
    super.addOption(kafkaGroup);
    super.addOption(output);
    super.addOption(hiddenMetric);
    super.addOption(hiddenLocality);
    super.addOption(hiddenWeights);
    super.addOption(hiddenThreshold);
    super.addOption(potentialMetric);
    super.addOption(potentialLocality);
    super.addOption(potentialWeight);
    super.addOption(potentialThreshold);
    super.addOption(ewmaFactor);
    super.addOption(neo4jHostname);
    super.addOption(neo4jUsername);
    super.addOption(neo4jPassword);
    super.addOption(parallelism);
  }

  /**
   * Builds the option `version`.
   * @return the option.
   */
  private Option optVersion() {
    return Option.builder("v")
        .longOpt("version")
        .desc(DESCRIPTION_VERSION)
        .required(false)
        .hasArg(false)
        .build();
  }

  /**
   * Builds the option `help`.
   * @return the option.
   */
  private Option optHelp() {
    return Option.builder("h")
        .longOpt("help")
        .desc(DESCRIPTION_HELP)
        .required(false)
        .hasArg(false)
        .build();
  }

  /**
   * Builds the option `config`.
   * @return the option.
   */
  private Option optConfig() {
    return Option.builder("c")
        .longOpt("config")
        .desc(DESCRIPTION_CONFIG)
        .required(false)
        .hasArg(true)
        .numberOfArgs(1)
        .argName("YAML-FILE")
        .build();
  }

  /**
   * Builds the option `source`.
   * @return the option.
   */
  private Option optSource() {
    return Option.builder()
        .longOpt("source")
        .desc(DESCRIPTION_SOURCE)
        .required(false)
        .hasArg(true)
        .numberOfArgs(1)
        .argName("FILE|KAFKA")
        .build();
  }

  /**
   * Builds the option `dataset`.
   * @return the option.
   */
  private Option optDataset() {
    return Option.builder()
        .longOpt("dataset")
        .desc(DESCRIPTION_DATASET)
        .required(false)
        .hasArg(true)
        .numberOfArgs(1)
        .argName("FILE")
        .build();
  }

  /**
   * Builds the option `kafkaTopic`.
   * @return the option.
   */
  private Option optKafkaTopic() {
    return Option.builder()
        .longOpt("kafkaTopic")
        .desc(DESCRIPTION_KAFKA_TOPIC)
        .required(false)
        .hasArg(true)
        .numberOfArgs(1)
        .argName("topic")
        .build();
  }

  /**
   * Builds the option `kafkaBootstrap`.
   * @return the option.
   */
  private Option optKafkaBootstrap() {
    return Option.builder()
        .longOpt("kafkaBootstrap")
        .desc(DESCRIPTION_KAFKA_BOOTSTRAP)
        .required(false)
        .hasArg(true)
        .numberOfArgs(1)
        .argName("BOOTSTRAP SERVERS")
        .build();
  }

  /**
   * Builds the option `kafkaZookeper`.
   * @return the option.
   */
  private Option optKafkaZookeper() {
    return Option.builder()
        .longOpt("kafkaZookeper")
        .desc(DESCRIPTION_KAFKA_ZOOKEPER)
        .required(false)
        .hasArg(true)
        .numberOfArgs(1)
        .argName("ZOOKEPER CONNECT")
        .build();
  }

  /**
   * Builds the option `kafkaGroup`.
   * @return the option.
   */
  private Option optKafkaGroup() {
    return Option.builder()
        .longOpt("kafkaGroup")
        .desc(DESCRIPTION_KAFKA_GROUP)
        .required(false)
        .hasArg(true)
        .numberOfArgs(1)
        .argName("KAFKA GROUP ID")
        .build();
  }

  /**
   * Builds the option `output`.
   * @return the option.
   */
  private Option optOutput() {
    return Option.builder()
        .longOpt("output")
        .desc(DESCRIPTION_OUTPUT)
        .required(false)
        .hasArg(true)
        .numberOfArgs(1)
        .argName("FILE")
        .build();
  }

  /**
   * Builds the option `hiddenMetric`.
   * @return the option.
   */
  private Option optHiddenMetric() {
    return Option.builder()
        .longOpt("hiddenMetric")
        .desc(DESCRIPTION_HIDDEN_METRIC)
        .required(false)
        .hasArg(true)
        .numberOfArgs(1)
        .argName("LOCAL|QUASI_LOCAL|WEIGHTED_QUASI_LOCAL")
        .build();
  }

  /**
   * Builds the option `hiddenLocality`.
   * @return the option.
   */
  private Option optHiddenLocality() {
    return Option.builder()
        .longOpt("hiddenLocality")
        .desc(DESCRIPTION_HIDDEN_LOCALITY)
        .required(false)
        .hasArg(true)
        .numberOfArgs(1)
        .argName("NUMBER")
        .build();
  }

  /**
   * Builds the option `hiddenWeights`.
   * @return the option.
   */
  private Option optHiddenWeights() {
    return Option.builder()
        .longOpt("hiddenWeights")
        .desc(DESCRIPTION_HIDDEN_WEIGHTS)
        .required(false)
        .hasArg(true)
        .numberOfArgs(1)
        .argName("NUMBER,NUMBER,...")
        .build();
  }

  /**
   * Builds the option `hiddenThreshold`.
   * @return the option.
   */
  private Option optHiddenThreshold() {
    return Option.builder()
        .longOpt("hiddenThreshold")
        .desc(DESCRIPTION_HIDDEN_THRESHOLD)
        .required(false)
        .hasArg(true)
        .numberOfArgs(1)
        .argName("NUMBER")
        .build();
  }

  /**
   * Builds the option `potentialMetric`.
   * @return the option.
   */
  private Option optPotentialMetric() {
    return Option.builder()
        .longOpt("potentialMetric")
        .desc(DESCRIPTION_POTENTIAL_METRIC)
        .required(false)
        .hasArg(true)
        .numberOfArgs(1)
        .argName("LOCAL|QUASI_LOCAL|WEIGHTED_QUASI_LOCAL")
        .build();
  }

  /**
   * Builds the option `potentialLocality`.
   * @return the option.
   */
  private Option optPotentialLocality() {
    return Option.builder()
        .longOpt("potentialLocality")
        .desc(DESCRIPTION_POTENTIAL_LOCALITY)
        .required(false)
        .hasArg(true)
        .numberOfArgs(1)
        .argName("NUMBER")
        .build();
  }

  /**
   * Builds the option `potentialWeights`.
   * @return the option.
   */
  private Option optPotentialWeights() {
    return Option.builder()
        .longOpt("potentialWeights")
        .desc(DESCRIPTION_POTENTIAL_WEIGHTS)
        .required(false)
        .hasArg(true)
        .numberOfArgs(1)
        .argName("NUMBER,NUMBER,...")
        .build();
  }

  /**
   * Builds the option `potentialThreshold`.
   * @return the option.
   */
  private Option optPotentialThreshold() {
    return Option.builder()
        .longOpt("potentialThreshold")
        .desc(DESCRIPTION_POTENTIAL_THRESHOLD)
        .required(false)
        .hasArg(true)
        .numberOfArgs(1)
        .argName("NUMBER")
        .build();
  }

  /**
   * Builds the option `ewmaFactor`.
   * @return the option.
   */
  private Option optEwmaFactor() {
    return Option.builder()
        .longOpt("ewmaFactor")
        .desc(DESCRIPTION_EWMA_FACTOR)
        .required(false)
        .hasArg(true)
        .numberOfArgs(1)
        .argName("NUMBER")
        .build();
  }

  /**
   * Builds the option `neo4jHostname`.
   * @return the option.
   */
  private Option optNeo4jHostname() {
    return Option.builder()
        .longOpt("neo4jHostname")
        .desc(DESCRIPTION_NEO4J_HOSTNAME)
        .required(false)
        .hasArg(true)
        .numberOfArgs(1)
        .argName("HOST:PORT")
        .build();
  }

  /**
   * Builds the option `neo4jUsername`.
   * @return the option.
   */
  private Option optNeo4JUsername() {
    return Option.builder()
        .longOpt("neo4jUsername")
        .desc(DESCRIPTION_NEO4J_USERNAME)
        .required(false)
        .hasArg(true)
        .numberOfArgs(1)
        .argName("USERNAME")
        .build();
  }

  /**
   * Builds the option `neo4jPassword`.
   * @return the option.
   */
  private Option optNeo4JPassword() {
    return Option.builder()
        .longOpt("neo4jPassword")
        .desc(DESCRIPTION_NEO4J_PASSWORD)
        .required(false)
        .hasArg(true)
        .numberOfArgs(1)
        .argName("PASSWORD")
        .build();
  }

  /**
   * Builds the option `parallelism`.
   * @return the option.
   */
  private Option optParallelism() {
    return Option.builder()
        .longOpt("parallelism")
        .desc(DESCRIPTION_PARALLELISM)
        .required(false)
        .hasArg(true)
        .numberOfArgs(1)
        .argName("NUMBER")
        .build();
  }

}
