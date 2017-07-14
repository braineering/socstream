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

import com.acmutv.socstream.common.tuple.RichSensorEvent;
import com.acmutv.socstream.query1.tuple.PlayerRunningStatistics;
import com.acmutv.socstream.tool.physics.PhysicsUtil;
import org.apache.flink.api.common.functions.AggregateFunction;
import org.apache.flink.api.java.tuple.Tuple3;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The operator that calculates players running statistics (with window).
 *
 * @author Giacomo Marciani {@literal <gmarciani@acm.org>}
 * @author Michele Porretta {@literal <mporretta@acm.org>}
 * @since 1.0
 */
public class PlayerRunningStatisticsCalculatorAggregator implements AggregateFunction<RichSensorEvent,Tuple3<Long,Double,Double>,PlayerRunningStatistics> {

  /**
   * The logger.
   */
  private static final Logger LOG = LoggerFactory.getLogger(PlayerRunningStatisticsCalculatorAggregator.class);

  /**
   * Creates a new accumulator, starting a new aggregate.
   * <p>
   * <p>The new accumulator is typically meaningless unless a value is added
   * via {@link #add(Object, Object)}.
   * <p>
   * <p>The accumulator is the state of a running aggregation. When a program has multiple
   * aggregates in progress (such as per key and window), the state (per key and window)
   * is the size of the accumulator.
   *
   * @return A new accumulator, corresponding to an empty aggregate.
   */
  @Override
  public Tuple3<Long,Double,Double> createAccumulator() {
    return new Tuple3<>(0L, 0.0, 0.0);
  }

  /**
   * Adds the given value to the given accumulator.
   *
   * @param event       The value to add
   * @param accumulator The accumulator (numEvents,totalDistance,averageSpeed)
   */
  @Override
  public void add(RichSensorEvent event, Tuple3<Long,Double,Double> accumulator) {
    long numEvents = ++accumulator.f0;

    //LOG.debug("IN ({}): {}", numEvents, event);

    final double distanceSpeed[] = PhysicsUtil.computeDistanceAndSpeed(event.getV(), event.getVx(), event.getVy(), event.getA(), event.getAx(), event.getAy());

    accumulator.f1 = accumulator.f1 + distanceSpeed[0];
    accumulator.f2 = ((accumulator.f2 * (numEvents - 1)) + distanceSpeed[1]) / numEvents;

    //LOG.debug("ACC: {}", accumulator);
  }

  /**
   * Gets the result of the aggregation from the accumulator.
   *
   * @param accumulator The accumulator of the aggregation
   * @return The final aggregation result.
   */
  @Override
  public PlayerRunningStatistics getResult(Tuple3<Long,Double,Double> accumulator) {
    return new PlayerRunningStatistics(0, 0, 0, accumulator.f1, accumulator.f2);
  }

  /**
   * Merges two accumulators, returning an accumulator with the merged state.
   * <p>
   * <p>This function may reuse any of the given accumulators as the target for the merge
   * and return that. The assumption is that the given accumulators will not be used any
   * more after having been passed to this function.
   *
   * @param a An accumulator to merge
   * @param b Another accumulator to merge
   * @return The accumulator with the merged state
   */
  @Override
  public Tuple3<Long,Double,Double> merge(Tuple3<Long,Double,Double> a, Tuple3<Long,Double,Double> b) {
    return null;
  }
}
