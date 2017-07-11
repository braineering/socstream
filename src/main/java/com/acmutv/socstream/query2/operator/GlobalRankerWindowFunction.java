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

import com.acmutv.socstream.query2.tuple.PlayerSpeedStatistics;
import com.acmutv.socstream.query2.tuple.PlayersSpeedRanking;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.flink.api.common.functions.RichFlatMapFunction;
import org.apache.flink.streaming.api.functions.windowing.AllWindowFunction;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;
import org.apache.flink.util.Collector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Set;

/**
 * The operator that calculates the global ranking of players by average speed.
 *
 * @author Giacomo Marciani {@literal <gmarciani@acm.org>}
 * @author Michele Porretta {@literal <mporretta@acm.org>}
 * @since 1.0
 */
@Data
public class GlobalRankerWindowFunction implements AllWindowFunction<PlayerSpeedStatistics, PlayersSpeedRanking, TimeWindow> {

  /**
   * The logger.
   */
  private static final Logger LOG = LoggerFactory.getLogger(GlobalRankerWindowFunction.class);

  /**
   * The size of the top-K ranking.
   */
  private int rankSize;

  /**
   * Creates a new {@link GlobalRankerWindowFunction} with the specified rank size.
   * @param rankSize the size of the top-k ranking.
   */
  public GlobalRankerWindowFunction(int rankSize) {
    this.rankSize = rankSize;
  }

  /**
   * Evaluates the window and outputs none or several elements.
   *
   * @param window The window that is being evaluated.
   * @param values The elements in the window being evaluated.
   * @param out    A collector for emitting elements.
   * @throws Exception The function may throw exceptions to fail the program and trigger recovery.
   */
  @Override
  public void apply(TimeWindow window, Iterable<PlayerSpeedStatistics> values, Collector<PlayersSpeedRanking> out) throws Exception {

    PlayersSpeedRanking ranking = new PlayersSpeedRanking();
    ranking.setTsStart(window.getStart());
    ranking.setTsStop(window.getEnd());

    out.collect(ranking);
  }
}
