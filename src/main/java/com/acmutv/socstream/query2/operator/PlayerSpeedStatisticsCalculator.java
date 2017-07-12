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
import com.acmutv.socstream.query1.operator.PlayerRunningStatisticsCalculator;
import com.acmutv.socstream.query1.tuple.PlayerRunningStatistics;
import com.acmutv.socstream.query2.tuple.PlayerSpeedStatistics;
import com.acmutv.socstream.tool.physics.PhysicsUtil;
import org.apache.flink.api.common.functions.RichFlatMapFunction;
import org.apache.flink.util.Collector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The operator that calculates players running statistics (without window).
 *
 * @author Giacomo Marciani {@literal <gmarciani@acm.org>}
 * @author Michele Porretta {@literal <mporretta@acm.org>}
 * @since 1.0
 */
public class PlayerSpeedStatisticsCalculator extends RichFlatMapFunction<RichSensorEvent,PlayerSpeedStatistics> {

  /**
   * The logger.
   */
  private static final Logger LOG = LoggerFactory.getLogger(PlayerSpeedStatisticsCalculator.class);

  /**
   * The special tuple signaling the end of stream.
   */
  public static final PlayerSpeedStatistics END_OF_STREAM = new PlayerSpeedStatistics(0L, Long.MAX_VALUE, 0L, 0L);

  /**
   * Number of events.
   */
  private long numEvents = 0;

  /**
   * Player average speed distance (m/s).
   */
  private double averageSpeed = 0.0;

  /**
   * The tuple signaling the end of stream.
   */
  private RichSensorEvent eos;

  /**
   * Creates a new operator.
   * @param eos the tuple signaling the end of stream.
   */
  public PlayerSpeedStatisticsCalculator(RichSensorEvent eos) {
    this.eos = eos;
  }

  @Override
  public void flatMap(RichSensorEvent event, Collector<PlayerSpeedStatistics> out) throws Exception {
    if (event.equals(this.eos)) {
      LOG.debug("EOS RECEIVED");
      out.collect(new PlayerSpeedStatistics(0,0,0, this.averageSpeed));
      out.collect(END_OF_STREAM);
      super.close();
    }

    this.numEvents++;

    LOG.debug("IN ({}): {}", this.numEvents, event);

    final double speed = PhysicsUtil.computeSpeed(event.getV(), event.getVx(), event.getVy(), event.getA(), event.getAx(), event.getAy());

    this.averageSpeed = ((this.averageSpeed * (this.numEvents - 1)) + speed) / this.numEvents;

    LOG.debug("ACC: {} {}", this.numEvents, this.averageSpeed);
  }
}