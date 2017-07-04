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

package com.acmutv.socstream.query2;

import com.acmutv.socstream.common.keyer.RichSensorEventKeyer;
import com.acmutv.socstream.common.operator.IdentityMap;
import com.acmutv.socstream.common.sink.ConsoleWriterSink;
import com.acmutv.socstream.common.sink.FileWriterSink;
import com.acmutv.socstream.common.source.kafka.KafkaProperties;
import com.acmutv.socstream.common.source.kafka.RichSensorEventKafkaSource;
import com.acmutv.socstream.common.meta.Match;
import com.acmutv.socstream.common.meta.MatchService;
import com.acmutv.socstream.common.tuple.RichSensorEvent;
import com.acmutv.socstream.tool.runtime.RuntimeManager;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.Map;
import java.util.Set;

/**
 * The topology for query-2.
 * @author Giacomo Marciani {@literal <gmarciani@acm.org>}
 * @author Michele Porretta {@literal <mporretta@acm.org>}
 * @since 1.0
 * @see RuntimeManager
 */
public class TopologyQuery2 {

  /**
   * The program name.
   */
  public static final String PROGRAM_NAME = "query-2";

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
    final String kafkaTopic = parameter.get("kafka.topic", "socstream");
    final int parallelism = parameter.getInt("parallelism", 1);
    final long matchStart = parameter.getLong("match.start", 10753295594424116L);
    final long matchEnd = parameter.getLong("match.end", 14879639146403495L);
    final long matchIntervalStart = parameter.getLong("match.interval.start", 12557295594424116L);
    final long matchIntervalEnd = parameter.getLong("match.interval.end", 13086639146403495L);
    final Path metadataPath = FileSystems.getDefault().getPath(parameter.get("metadata", "./metadata.yml"));
    final Path outputPath = FileSystems.getDefault().getPath(parameter.get("output", PROGRAM_NAME + ".out"));
    final Match match = MatchService.fromYamlFile(metadataPath);
    final Set<Long> ignoredSensors = MatchService.collectIgnoredSensors(match);
    final Map<Long,Long> sid2Pid = MatchService.collectSid2Pid(match);

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
    System.out.println("Metadata: " + metadataPath);
    System.out.println("Output: " + outputPath);
    System.out.println("Parallelism: " + parallelism);
    System.out.println("Match Start: " + matchStart);
    System.out.println("Match End: " + matchEnd);
    System.out.println("Match Interval Start: " + matchIntervalStart);
    System.out.println("Match Interval End: " + matchIntervalEnd);
    System.out.println("Ignored Sensors: " + ignoredSensors);
    System.out.println("############################################################################");

    // TOPOLOGY
    DataStream<RichSensorEvent> sensorEvents = env.addSource(new RichSensorEventKafkaSource(kafkaTopic, kafkaProps,
        matchStart, matchEnd, matchIntervalStart, matchIntervalEnd, ignoredSensors, sid2Pid));

    DataStream<RichSensorEvent> out = sensorEvents.keyBy(new RichSensorEventKeyer()).flatMap(new IdentityMap<>());

    out.addSink(new FileWriterSink<>(outputPath.toString()));

    // EXECUTION
    env.execute(PROGRAM_NAME);
  }

}
