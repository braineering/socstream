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
package com.acmutv.socstream.query1.operator;

import com.acmutv.socstream.query1.tuple.PlayerRunningStatistics;
import org.apache.flink.streaming.api.functions.windowing.WindowFunction;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;
import org.apache.flink.util.Collector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The operator that calculates palyers running statistics (with window).
 *
 * @author Giacomo Marciani {@literal <gmarciani@acm.org>}
 * @author Michele Porretta {@literal <mporretta@acm.org>}
 * @since 1.0
 */
public class PlayerRunningStatisticsCalculatorWindowFunction implements WindowFunction<PlayerRunningStatistics,PlayerRunningStatistics,Long,TimeWindow> {

  /**
   * The logger.
   */
  private static final Logger LOG = LoggerFactory.getLogger(PlayerRunningStatisticsCalculatorWindowFunction.class);

  /**
   * Evaluates the window and outputs none or several elements.
   *
   * @param key    The key for which this window is evaluated.
   * @param window The window that is being evaluated.
   * @param inputs The elements in the window being evaluated.
   * @param out    A collector for emitting elements.
   * @throws Exception The function may throw exceptions to fail the program and trigger recovery.
   */
  @Override
  public void apply(Long key, TimeWindow window, Iterable<PlayerRunningStatistics> inputs, Collector<PlayerRunningStatistics> out) throws Exception {
    PlayerRunningStatistics stats = inputs.iterator().next();

    stats.setPid(key);
    stats.setTsStart(window.getStart());
    stats.setTsStop(window.getEnd());

    LOG.info("OUT: {}", stats);

    out.collect(stats);
  }
}
