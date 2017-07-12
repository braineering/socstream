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
import lombok.Data;
import org.apache.flink.api.common.ExecutionConfig;
import org.apache.flink.api.common.typeutils.TypeSerializer;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.windowing.AllWindowFunction;
import org.apache.flink.streaming.api.windowing.assigners.BaseAlignedWindowAssigner;
import org.apache.flink.streaming.api.windowing.assigners.WindowAssigner;
import org.apache.flink.streaming.api.windowing.triggers.Trigger;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;
import org.apache.flink.util.Collector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;

/**
 * The operator that calculates the global ranking of players by average speed.
 *
 * @author Giacomo Marciani {@literal <gmarciani@acm.org>}
 * @author Michele Porretta {@literal <mporretta@acm.org>}
 * @since 1.0
 */
@Data
@Deprecated
public class GlobalRankerWindowAssigner extends WindowAssigner<PlayerSpeedStatistics, TimeWindow> {

  /**
   * The logger.
   */
  private static final Logger LOG = LoggerFactory.getLogger(GlobalRankerWindowAssigner.class);

  /**
   * Returns a {@code Collection} of windows that should be assigned to the element.
   *
   * @param element   The element to which windows should be assigned.
   * @param timestamp The timestamp of the element.
   * @param context   The {@link WindowAssignerContext} in which the assigner operates.
   */
  @Override
  public Collection<TimeWindow> assignWindows(PlayerSpeedStatistics element, long timestamp, WindowAssignerContext context) {
    return null;
  }

  /**
   * Returns the default trigger associated with this {@code WindowAssigner}.
   *
   * @param env
   */
  @Override
  public Trigger<PlayerSpeedStatistics, TimeWindow> getDefaultTrigger(StreamExecutionEnvironment env) {
    return null;
  }

  /**
   * Returns a {@link TypeSerializer} for serializing windows that are assigned by
   * this {@code WindowAssigner}.
   *
   * @param executionConfig
   */
  @Override
  public TypeSerializer<TimeWindow> getWindowSerializer(ExecutionConfig executionConfig) {
    return null;
  }

  /**
   * Returns {@code true} if elements are assigned to windows based on event time,
   * {@code false} otherwise.
   */
  @Override
  public boolean isEventTime() {
    return false;
  }
}
