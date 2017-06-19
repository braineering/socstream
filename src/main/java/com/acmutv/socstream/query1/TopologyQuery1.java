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

package com.acmutv.socstream.query1;

import com.acmutv.socstream.common.source.kafka.KafkaProperties;
import com.acmutv.socstream.common.source.kafka.SensorEventKafkaSource;
import com.acmutv.socstream.common.tuple.SensorEvent;
import com.acmutv.socstream.query1.operator.WordTokenizer;
import com.acmutv.socstream.query1.operator.WordCountReducer;
import com.acmutv.socstream.query1.tuple.WordWithCount;
import com.acmutv.socstream.tool.runtime.RuntimeManager;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.time.Time;

/**
 * The topology for query-1.
 * @author Giacomo Marciani {@literal <gmarciani@acm.org>}
 * @author Michele Porretta {@literal <mporretta@acm.org>}
 * @since 1.0
 * @see RuntimeManager
 */
public class TopologyQuery1 {

  /**
   * The program name.
   */
  public static final String PROGRAM_NAME = "query-1";

  /**
   * The program description.
   */
  public static final String PROGRAM_DESCRIPTION = "Insert here program description";

  /**
   * The program main method.
   * @param args the command line arguments.
   */
  public static void main(String[] args) throws Exception {

    // CONFIGURATION
    ParameterTool parameter = ParameterTool.fromArgs(args);
    final String kafkaZookeeper = parameter.get("kafka.zookeeper", "localhost:2181");
    final String kafkaBootstrap = parameter.get("kafka.bootstrap", "localhost:9092");
    final String kafkaTopic = parameter.get("kafka.topic", "socstream.topic.sensorEvents");
    final int parallelism = parameter.getInt("parallelism", 1);

    // ENVIRONMENT
    final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
    env.setParallelism(parallelism);
    final KafkaProperties kafkaProps = new KafkaProperties(kafkaBootstrap, kafkaZookeeper);

    // CONFIGURATION RESUME
    System.out.println("############################################################################");
    System.out.printf("%s\n", PROGRAM_NAME);
    System.out.println("----------------------------------------------------------------------------");
    System.out.printf("%s\n", PROGRAM_DESCRIPTION);
    System.out.println("****************************************************************************");
    System.out.println("Kafka Zookeeper: " + kafkaZookeeper);
    System.out.println("Kafka Bootstrap: " + kafkaBootstrap);
    System.out.println("Kafka Topic: " + kafkaTopic);
    System.out.println("Parallelism: " + parallelism);
    System.out.println("############################################################################");

    // TOPOLOGY
    DataStream<SensorEvent> sensorEvents = env.addSource(new SensorEventKafkaSource(kafkaTopic, kafkaProps));

    sensorEvents.print().setParallelism(1);

    // EXECUTION
    env.execute(PROGRAM_NAME);
  }

}
