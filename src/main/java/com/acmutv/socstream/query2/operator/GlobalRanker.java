/*
  The MIT License (MIT)

  Copyright (c) 2017 Giacomo Marciani and Michele Porretta

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
package com.acmutv.socstream.query2.operator;

import com.acmutv.socstream.common.tuple.RichSensorEvent;
import com.acmutv.socstream.query2.tuple.PlayerSpeedStatistics;
import com.acmutv.socstream.query2.tuple.PlayersSpeedRanking;
import com.acmutv.socstream.query2.tuple.RankingElement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.flink.api.common.functions.RichFlatMapFunction;
import org.apache.flink.util.Collector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * The operator that calculates the global ranking of players by average speed.
 *
 * @author Giacomo Marciani {@literal <gmarciani@acm.org>}
 * @author Michele Porretta {@literal <mporretta@acm.org>}
 * @since 1.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Deprecated
public class GlobalRanker extends RichFlatMapFunction<PlayerSpeedStatistics,PlayersSpeedRanking> {

  /**
   * The logger.
   */
  private static final Logger LOG = LoggerFactory.getLogger(GlobalRanker.class);

  /**
   * The size of the top-K ranking.
   */
  private int rankSize;

  /**
   * The tuple signaling the end of stream.
   */
  private PlayerSpeedStatistics eos;

  /**
   * The output: ranking.
   */
  private PlayersSpeedRanking ranking = new PlayersSpeedRanking();

  /**
   * Creates a new {@link GlobalRanker} with the specified rank size.
   * @param rankSize the size of the top-k ranking.
   * @param eos the tuple signaling the end of stream.
   */
  public GlobalRanker(int rankSize, PlayerSpeedStatistics eos) {
    this.rankSize = rankSize;
    this.eos = eos;
  }

  @Override
  public void flatMap(PlayerSpeedStatistics stats, Collector<PlayersSpeedRanking> out) throws Exception {
    /*if (stats.equals(this.eos)) {
      LOG.debug("EOS RECEIVED");
      out.collect(new PlayerSpeedStatistics(0,0,0, this.averageSpeed));
      close();
    }

    LOG.debug("IN: {}", values);

    List<RankingElement> tmp = new ArrayList<>();
    for (PlayerSpeedStatistics stat : values) {
      tmp.add(new RankingElement(stat.getPid(), stat.getAverageSpeed()));
    }
    tmp.sort((e1,e2) -> e2.compareTo(e1));

    this.ranking.setTsStart(window.getStart());
    this.ranking.setTsStop(window.getEnd());
    this.ranking.setRank(tmp.subList(0, this.rankSize));

    out.collect(this.ranking);
    */
  }
}
