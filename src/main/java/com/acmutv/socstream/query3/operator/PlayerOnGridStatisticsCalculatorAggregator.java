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
package com.acmutv.socstream.query3.operator;

import com.acmutv.socstream.common.tool.ComputeCenterOfGravity;
import com.acmutv.socstream.common.tool.GridTool;
import com.acmutv.socstream.common.tuple.Coordinate;
import com.acmutv.socstream.common.tuple.GridCoordinate;
import com.acmutv.socstream.common.tuple.PositionSensorEvent;
import com.acmutv.socstream.query3.tuple.PlayerGridStatistics;
import org.apache.flink.api.common.functions.AggregateFunction;
import org.apache.flink.api.java.tuple.Tuple4;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * The operator that calculates palyers running statistics (with window).
 *
 * @author Giacomo Marciani {@literal <gmarciani@acm.org>}
 * @author Michele Porretta {@literal <mporretta@acm.org>}
 * @since 1.0
 */
public class PlayerOnGridStatisticsCalculatorAggregator implements AggregateFunction<PositionSensorEvent,Tuple4<Long,Long,GridCoordinate,Map<String,Long>>,PlayerGridStatistics> {

  /**
   * The logger.
   */
  private static final Logger LOG = LoggerFactory.getLogger(PlayerOnGridStatisticsCalculatorAggregator.class);

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
  public Tuple4<Long,Long,GridCoordinate,Map<String,Long>> createAccumulator() {
    return new Tuple4<Long,Long,GridCoordinate,Map<String,Long>>(0L, 0L, new GridCoordinate(0,0,new Coordinate(0,0)), new HashMap<>());
  }

  /**
   * Adds the given value to the given accumulator.
   *
   * @param event       The value to add
   * @param accumulator The accumulator to add the value to
   */
  @Override
  public void add(PositionSensorEvent event, Tuple4<Long,Long,GridCoordinate,Map<String,Long>> accumulator) {
    long numEvents = ++accumulator.f0;

    //LOG.info("IN_1 ({}): {}", numEvents, event);

    long x = event.getX();
    long y = event.getY();
    long currentTimestamp = event.getTs();

    long lastTs = accumulator.f1;
    long cellLifeTime = 0L;
    long newCellLifeTime = 0L;

    GridCoordinate lastCell = accumulator.f2;
    Coordinate currentCenter = ComputeCenterOfGravity.computeWithCell(numEvents,x,y,lastCell);
    GridCoordinate currentCell = GridTool.computeCell(currentCenter);
    String cellkey = currentCell.getKey();

    if(currentCell.equals(lastCell)){
      cellLifeTime = accumulator.f3.get(cellkey);
      newCellLifeTime = cellLifeTime + (currentTimestamp - lastTs);
      accumulator.f3.put(cellkey,newCellLifeTime);
    } else {
      accumulator.f3.put(cellkey, 0L);
    }

    accumulator.f2 = currentCell;
    accumulator.f1 = currentTimestamp;

    //LOG.info("ACC: {}", accumulator);
  }

  /**
   * Gets the result of the aggregation from the accumulator.
   *
   * @param accumulator The accumulator of the aggregation
   * @return The final aggregation result.
   */
  @Override
  public PlayerGridStatistics getResult(Tuple4<Long,Long,GridCoordinate,Map<String,Long>> accumulator) {
    return new PlayerGridStatistics(0, 0, accumulator.f3);
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
  public Tuple4<Long,Long,GridCoordinate,Map<String,Long>> merge(Tuple4<Long,Long,GridCoordinate,Map<String,Long>> a,
                                                                 Tuple4<Long,Long,GridCoordinate,Map<String,Long>> b) {
    return null;
  }
}
