/*
  The MIT License (MIT)

  Copyright (c) 2016 Giacomo Marciani and Michele Porretta

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

package com.acmutv.socstream.core.query3.tuple;

import com.acmutv.socstream.common.tuple.GridCoordinate;
import com.acmutv.socstream.query3.tuple.PlayerGridStatistics;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * JUnit test suite for {@link PlayerGridStatistics}.
 * @author Giacomo Marciani {@literal <gmarciani@acm.org>}
 * @author Michele Porretta {@literal <mporretta@acm.org>}
 * @since 1.0
 * @see PlayerGridStatistics
 */
public class PlayerGridStatisticsTest {

  private static final Logger LOGGER = LoggerFactory.getLogger(PlayerGridStatisticsTest.class);

  /**
   * Tests serialization of {@link PlayerGridStatistics}.
   */
  @Test
  public void test_serialize_grid_statistics() throws Exception {
    //test put first element (cell for a player)
    Map<String,Long> grid = new HashMap<>();
    grid.put("2;3",0L);
    PlayerGridStatistics test = new PlayerGridStatistics(0,1L,grid);
    String expected="1,0, NOT AVAILABLE";
    LOGGER.debug("PlayerGridStatistics serialized: " + expected);
    String actual = test.toString();
    Assert.assertEquals(expected, actual);
  }

  /**
   * Tests deserialization of {@link GridCoordinate} from a string representing an gridID
   */
  @Test
  public void test_valueOfAsSensorEvent() throws Exception {

    String stats ="9,1,1,10,2;3,1;1,1,1;2,3";
    PlayerGridStatistics gs = PlayerGridStatistics.valueOf(stats);
    String expected ="1,9";
    String actual = gs.toString();
    Assert.assertEquals(expected,actual);
  }
}