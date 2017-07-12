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

import com.acmutv.socstream.common.tool.ComputeCenterOfGravity;
import com.acmutv.socstream.common.tool.GridTool;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * JUnit test suite for {@link ComputeCenterOfGravity} and {@link GridTool}.
 * @author Giacomo Marciani {@literal <gmarciani@acm.org>}
 * @author Michele Porretta {@literal <mporretta@acm.org>}
 * @since 1.0
 * @see ComputeCenterOfGravity
 * @see GridTool
 */
public class ComputeCellTest {

  private static final Logger LOGGER = LoggerFactory.getLogger(ComputeCellTest.class);

  /**
   * Tests serialization of {@link ComputeCenterOfGravity} and {@link GridTool}.
   */
  @Test
  public void test_compute_cell() throws Exception {
    List<Coordinate> coordinates = new ArrayList<>();
    coordinates.add(new Coordinate(36736,5529));

    Coordinate base = coordinates.get(0);
    GridCoordinate gc = GridTool.computeCell(base);
    Assert.assertEquals(gc.getX(),9);
    Assert.assertEquals(gc.getY(),4);

    base = ComputeCenterOfGravity.computeWithCell(36861,5572, gc);
    gc = GridTool.computeCell(base);
    Assert.assertEquals(gc.getX(),9);
    Assert.assertEquals(gc.getY(),4);

    base = ComputeCenterOfGravity.computeWithCell(36737,5528, gc);
    gc = GridTool.computeCell(base);
    Assert.assertEquals(gc.getX(),9);
    Assert.assertEquals(gc.getY(),4);
  }
}