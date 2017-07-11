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
import org.apache.flink.api.common.functions.FoldFunction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The operator that calculates palyers running statistics (with window).
 *
 * @author Giacomo Marciani {@literal <gmarciani@acm.org>}
 * @author Michele Porretta {@literal <mporretta@acm.org>}
 * @since 1.0
 */
public class PlayerRunningStatisticsCalculatorFold implements FoldFunction<RichSensorEvent,PlayerRunningStatistics> {

  /**
   * The logger.
   */
  private static final Logger LOG = LoggerFactory.getLogger(PlayerRunningStatisticsCalculatorFold.class);

  /**
   * Number of events for PID.
   */
  private long events = 0;

  @Override
  public PlayerRunningStatistics fold(PlayerRunningStatistics stats, RichSensorEvent event) throws Exception {
    LOG.info("IN: {}", event);

    final double speedDistance[] = PhysicsUtil.computeSpeedDistance(event.getV(), event.getVx(), event.getVy(), event.getA(), event.getAx(), event.getAy());

    final double newAvgSpeed = ((stats.getAvgSpeed() * (this.events++)) + speedDistance[0]) / this.events;
    final double newDist = stats.getDist() + speedDistance[1];

    stats.setPid(event.getId());
    stats.setAvgSpeed(newAvgSpeed);
    stats.setDist(newDist);

    LOG.info("OUT: {}", stats);

    return stats;
  }
}
