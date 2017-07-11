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
import org.apache.flink.api.common.functions.FoldFunction;
import org.apache.flink.api.common.functions.RichFoldFunction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The operator that calculates palyers running statistics (with window).
 *
 * @author Giacomo Marciani {@literal <gmarciani@acm.org>}
 * @author Michele Porretta {@literal <mporretta@acm.org>}
 * @since 1.0
 */
public class PlayerStatisticsCalculatorFold implements FoldFunction<RichSensorEvent,PlayerRunningStatistics> {

  /**
   * The logger.
   */
  private static final Logger LOG = LoggerFactory.getLogger(PlayerStatisticsCalculatorFold.class);

  /**
   * Number of events for PID.
   */
  private long events = 0;

  /**
   * The time interval (frequency=50Hz).
   */
  private static final double DELTA_T = 1.0/50.0;

  /**
   * The time interval square (frequency=50Hz).
   */
  private static final double DELTA_T_SQUARE = Math.pow(DELTA_T, 2);

  @Override
  public PlayerRunningStatistics fold(PlayerRunningStatistics stats, RichSensorEvent event) throws Exception {
    double distX = event.getX() + (event.getVx() * DELTA_T) + (0.5 * event.getAx() * DELTA_T_SQUARE);
    double distY = event.getY() + (event.getVy() * DELTA_T) + (0.5 * event.getAy() * DELTA_T_SQUARE);
    double dist = Math.sqrt(Math.pow(distX, 2) + Math.pow(distY, 2));
    double newDist = stats.getDist() + dist;

    double speedX = event.getVx() + (event.getAx() * DELTA_T);
    double speedY = event.getVy() + (event.getAy() * DELTA_T);
    double speed = Math.sqrt(Math.pow(speedX, 2) + Math.pow(speedY, 2));
    double newAvgSpeed = ((stats.getAvgSpeed() * (this.events++)) + speed) / this.events;

    stats.setPid(event.getId());
    stats.setDist(newDist);
    stats.setAvgSpeed(newAvgSpeed);

    LOG.info("IN: {}", event);
    LOG.info("OUT: {}", stats);

    return stats;
  }
}
