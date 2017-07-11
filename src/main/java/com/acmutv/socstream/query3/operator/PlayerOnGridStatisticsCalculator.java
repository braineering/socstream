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
import com.acmutv.socstream.query3.tuple.GridStatistics;
import com.acmutv.socstream.query3.tuple.PlayerOccupation;
import org.apache.flink.api.common.functions.RichFlatMapFunction;
import org.apache.flink.util.Collector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * The operator that calculates palyers running statistics.
 *
 * @author Giacomo Marciani {@literal <gmarciani@acm.org>}
 * @author Michele Porretta {@literal <mporretta@acm.org>}
 * @since 1.0
 */
public class PlayerOnGridStatisticsCalculator extends RichFlatMapFunction<PositionSensorEvent,PlayerOccupation> {

  /**
   * The logger.
   */
  private static final Logger LOG = LoggerFactory.getLogger(PlayerOnGridStatisticsCalculator.class);

  /**
   * The Player Grid Map <PlayerId, PlayerGrid>
   */
  private Map<Long,GridStatistics> grid;

  @Override
  public void flatMap(PositionSensorEvent pSE, Collector<PlayerOccupation> collector) throws Exception {

    long pid = pSE.getId();
    long currentTimestamp = pSE.getTs();

    Coordinate currentCenter = ComputeCenterOfGravity.computeWithCell(
            pSE.getX(),pSE.getY(),grid.get(pid).getLastCell());

    GridCoordinate currentCell = GridTool.computeCell(currentCenter);

    if(grid.containsKey(pid)) {

      if(currentCell.equals(grid.get(pid).getLastCell()))
        grid.get(pid).upgradeTime(currentCell,currentTimestamp);
      else
        grid.get(pid).setLastCell(currentCell);

      grid.get(pid).setLastTimestamp(currentTimestamp);
    }
    else {
      Map<String,Long> newStats = new HashMap<>();
      newStats.put(currentCell.getKey(),0L);
      grid.put(pid,new GridStatistics(pid,currentTimestamp,currentTimestamp,currentCell,newStats));
    }
  }
}
