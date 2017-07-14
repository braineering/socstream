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
package com.acmutv.socstream.query1.operator;

import com.acmutv.socstream.query1.tuple.PlayerRunningStatistics;
import org.apache.flink.api.common.functions.RuntimeContext;
import org.apache.flink.streaming.connectors.elasticsearch.ElasticsearchSinkFunction;
import org.apache.flink.streaming.connectors.elasticsearch.RequestIndexer;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.Requests;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * A sink that writes {@link PlayerRunningStatistics} to Elasticsearch.
 *
 * @author Giacomo Marciani {@literal <gmarciani@acm.org>}
 * @since 1.0
 */
public class PlayerRunningStatisticsESSinkFunction implements ElasticsearchSinkFunction<PlayerRunningStatistics> {

  /**
   * The logger.
   */
  private static final Logger LOG = LoggerFactory.getLogger(PlayerRunningStatisticsESSinkFunction.class);

  /**
   * The name of Elasticsearch index.
   */
  private String indexName;

  /**
   * The name of Elasticsearch type.
   */
  private String typeName;

  /**
   * Creates a new {@link PlayerRunningStatisticsESSinkFunction} with the specified index and type.
   * @param indexName the name of Elasticsearch index.
   * @param typeName the name of Elasticsearch type.
   */
  public PlayerRunningStatisticsESSinkFunction(String indexName, String typeName) {
    this.indexName = indexName;
    this.typeName = typeName;
  }

  @Override
  public void process(PlayerRunningStatistics value, RuntimeContext ctx, RequestIndexer indexer) {
    indexer.add(this.createWindowWordRanking(value));
  }

  /**
   * Creates a new Elasticsearch request from the given element.
   * @param value the element to process.
   * @return the Elasticsearch request.
   */
  private IndexRequest createWindowWordRanking(PlayerRunningStatistics value) {
    Map<String,String> json = new HashMap<>();
    json.put("tsStart", String.valueOf(value.getTsStart()));
    json.put("tsStop", String.valueOf(value.getTsStop()));
    json.put("pid", String.valueOf(value.getPid()));
    json.put("totalDistance", String.valueOf(value.getTotalDistance()));
    json.put("averageSpeed", String.valueOf(value.getAverageSpeed()));

    LOG.debug("JSON: {}", json);

    return Requests.indexRequest()
        .index(this.indexName)
        .type(this.typeName)
        .source(json);
  }
}