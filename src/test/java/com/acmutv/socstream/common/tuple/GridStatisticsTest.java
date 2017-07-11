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

package com.acmutv.socstream.common.tuple;

import com.acmutv.socstream.query3.tuple.GridStatistics;
import com.acmutv.socstream.query3.tuple.PlayerOccupation;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * JUnit test suite for {@link PlayerOccupation}.
 * @author Giacomo Marciani {@literal <gmarciani@acm.org>}
 * @author Michele Porretta {@literal <mporretta@acm.org>}
 * @since 1.0
 * @see PlayerOccupation
 */
public class GridStatisticsTest {

  private static final Logger LOGGER = LoggerFactory.getLogger(GridStatisticsTest.class);

  /**
   * Tests serialization of {@link GridStatistics}.
   */
  @Test
  public void test_serialize_grid_statistics() throws Exception {
    //test put first element (cell for a player)
    Map<String,Long> grid = new HashMap<>();
    grid.put("2;3",0L);
    GridStatistics test = new GridStatistics(0,1L,1L,new GridCoordinate(2L,3L),grid);
    String expected="1,0,2;3,0";
    LOGGER.debug("GridStatistics serialized: " + expected);
    String actual = test.toString();
    Assert.assertEquals(expected, actual);

    //test upgrade time for the first element
    test.upgradeTime(new GridCoordinate(2L,3L),2L);
    expected ="1,0,2;3,1";
    test.setLastTimestamp(2L);
    actual = test.toString();
    Assert.assertEquals(expected, actual);

    //test change cell and upgrade time in the same cell (two consequential events)
    test.setLastCell(new GridCoordinate(2,4));
    test.upgradeTime(new GridCoordinate(2,4),3L);
    expected ="1,0,2;3,1,2;4,1";
    actual = test.toString();
    Assert.assertEquals(expected, actual);
  }

  /**
   * Tests deserialization of {@link GridCoordinate} from a string representing an gridID
   */
  @Test
  public void test_valueOfAsSensorEvent() throws Exception {

    String stats ="9,1,1,2;3,1;1,1,1;2,3";
    GridStatistics gs = GridStatistics.valueOf(stats);
    String expected ="1,9";
    String actual = gs.toString();
    Assert.assertEquals(expected,actual);
  }
}