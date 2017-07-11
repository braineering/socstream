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
public class GridCoordinateTest {

  private static final Logger LOGGER = LoggerFactory.getLogger(GridCoordinateTest.class);

  /**
   * Tests serialization of {@link GridCoordinate}.
   */
  @Test
  public void test_serialize_grid_coordinate() throws Exception {
    List<GridCoordinate> coordinates = new ArrayList<>();
    coordinates.add(new GridCoordinate(1,2));
    coordinates.add(new GridCoordinate(0,0));
    coordinates.add(new GridCoordinate(0,1));
    coordinates.add(new GridCoordinate(2,0));
    coordinates.add(new GridCoordinate(3,0));
    coordinates.add(new GridCoordinate(3,6));

    for (GridCoordinate expected : coordinates) {
      LOGGER.debug("GridCoordinate serialized: " + expected);
      String str = expected.toString();
      GridCoordinate actual = GridCoordinate.valueOf(str);
      Assert.assertEquals(expected.toString(), actual.toString());
    }
  }

  /**
   * Tests deserialization of {@link GridCoordinate} from a string representing an gridID
   */
  @Test
  public void test_valueOfAsSensorEvent() throws Exception {

    List<GridCoordinate> coordinates = new ArrayList<>();
    coordinates.add(new GridCoordinate(1,2));
    coordinates.add(new GridCoordinate(0,0));
    coordinates.add(new GridCoordinate(0,1));
    coordinates.add(new GridCoordinate(2,0));
    coordinates.add(new GridCoordinate(3,0));
    coordinates.add(new GridCoordinate(3,6));

    Map<String,String> IDs = new HashMap<>();
    IDs.put(coordinates.get(0).getKey(),"1;2");
    IDs.put(coordinates.get(1).getKey(),"0;0");
    IDs.put(coordinates.get(2).getKey(),"0;1");
    IDs.put(coordinates.get(3).getKey(),"2;0");
    IDs.put(coordinates.get(4).getKey(),"3;0");
    IDs.put(coordinates.get(5).getKey(),"3;6");

    for (GridCoordinate actual : coordinates) {
      LOGGER.debug("GridCoordinate deserialized: " + actual);
      String expected = IDs.get(actual.getKey());
      String actualID = actual.getKey();
      Assert.assertEquals(expected,actualID);
    }
  }


}