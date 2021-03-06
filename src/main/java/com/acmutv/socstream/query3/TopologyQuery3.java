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

package com.acmutv.socstream.query3;

import com.acmutv.socstream.query3.operator.PositionSensorEventKeyer;
import com.acmutv.socstream.common.meta.Match;
import com.acmutv.socstream.common.meta.MatchService;
import com.acmutv.socstream.common.sink.es.ESProperties;
import com.acmutv.socstream.common.sink.es.ESSink;
import com.acmutv.socstream.common.source.kafka.KafkaProperties;
import com.acmutv.socstream.common.source.kafka.PositionSensorEventKafkaSource;
import com.acmutv.socstream.common.tuple.PositionSensorEvent;
import com.acmutv.socstream.query3.operator.*;
import com.acmutv.socstream.query3.tuple.PlayerGridStatistics;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.core.fs.FileSystem;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.time.Time;

import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * The topology for query-3.
 * @author Giacomo Marciani {@literal <gmarciani@acm.org>}
 * @author Michele Porretta {@literal <mporretta@acm.org>}
 * @since 1.0
 */
public class TopologyQuery3 {

  /**
   * The program name.
   */
  public static final String PROGRAM_NAME = "query-3";

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
    final Path outputPath = FileSystems.getDefault().getPath(parameter.get("output", PROGRAM_NAME + ".out"));
    final String elasticsearch = parameter.get("elasticsearch", null);
    final Path metadataPath = FileSystems.getDefault().getPath(parameter.get("metadata", "./metadata.yml"));
    final long windowSize = parameter.getLong("windowSize", 70);
    final TimeUnit windowUnit = TimeUnit.valueOf(parameter.get("windowUnit", "MINUTES"));
    final long matchStart = parameter.getLong("match.start", 10753295594424116L);
    final long matchEnd = parameter.getLong("match.end", 14879639146403495L);
    final long matchIntervalStart = parameter.getLong("match.interval.start", 12557295594424116L);
    final long matchIntervalEnd = parameter.getLong("match.interval.end", 13086639146403495L);
    final int parallelism = parameter.getInt("parallelism", 1);

    final Match match = MatchService.fromYamlFile(metadataPath);
    final Set<Long> ignoredSensors = MatchService.collectIgnoredSensors(match);
    final Map<Long,Long> sid2Pid = MatchService.collectSid2Pid(match);

    // ENVIRONMENT
    final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
    env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);
    final KafkaProperties kafkaProps = new KafkaProperties(kafkaBootstrap);
    final ESProperties elasticsearchProps = ESProperties.fromPropString(elasticsearch);

    // CONFIGURATION RESUME
    System.out.println("############################################################################");
    System.out.printf("%s\n", PROGRAM_NAME);
    System.out.println("----------------------------------------------------------------------------");
    System.out.printf("%s\n", PROGRAM_DESCRIPTION);
    System.out.println("****************************************************************************");
    System.out.println("Kafka Zookeeper: " + kafkaZookeeper);
    System.out.println("Kafka Bootstrap: " + kafkaBootstrap);
    System.out.println("Kafka Topic: " + kafkaTopic);
    System.out.println("Output: " + outputPath);
    System.out.println("Elasticsearch: " + elasticsearch);
    System.out.println("Metadata: " + metadataPath);
    System.out.println("Window: " + windowSize + " " + windowUnit);
    System.out.println("Match Start: " + matchStart);
    System.out.println("Match End: " + matchEnd);
    System.out.println("Match Interval Start: " + matchIntervalStart);
    System.out.println("Match Interval End: " + matchIntervalEnd);
    System.out.println("Ignored Sensors: " + ignoredSensors);
    System.out.println("Parallelism: " + parallelism);
    System.out.println("############################################################################");

    // TOPOLOGY
    DataStream<PositionSensorEvent> sensorEvents = env.addSource(
        new PositionSensorEventKafkaSource(kafkaTopic, kafkaProps, matchStart, matchEnd,
            matchIntervalStart, matchIntervalEnd, ignoredSensors, sid2Pid
        ).assignTimestampsAndWatermarks(new PositionSensorEventTimestampExtractor())).setParallelism(1);

    DataStream<PlayerGridStatistics> statistics = sensorEvents.keyBy(new PositionSensorEventKeyer())
        .timeWindow(Time.of(windowSize, windowUnit))
        .aggregate(new PlayerOnGridStatisticsCalculatorAggregator(), new PlayerOnGridStatisticsCalculatorWindowFunction())
        .setParallelism(parallelism);

    statistics.writeAsText(outputPath.toAbsolutePath().toString(), FileSystem.WriteMode.OVERWRITE).setParallelism(1);

    if (elasticsearch != null) {
      statistics.addSink(new ESSink<>(elasticsearchProps,
          new PlayerGridStatisticsESSinkFunction(elasticsearchProps.getIndexName(), elasticsearchProps.getTypeName()))
      ).setParallelism(1);
    }

    // EXECUTION
    env.execute(PROGRAM_NAME);
  }

}
