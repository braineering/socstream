/*
  The MIT License (MIT)

  Copyright (c) 2017 Giacomo Marciani

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
package com.acmutv.socstream.common.tool;

import com.acmutv.socstream.common.tuple.Coordinate;
import com.acmutv.socstream.common.tuple.GridCoordinate;

/**
 * This class gives the center of gravity between 2 points with their coordinates.
 * The origin of the axes coincides with the center of the game field
 * @author Giacomo Marciani {@literal <gmarciani@acm.org>}
 * @author Michele Porretta {@literal <mporretta@acm.org>}
 * @since 1.0
 */
public class ComputeCenterOfGravity {

  /**
   * Compute Center of Gravity between a point (x,y) and a GridCoordinate
   * @param currentX
   * @param currentY
   * @param last
   * @return
   */
  public static Coordinate computeWithCell(long numEvents, long currentX, long currentY, GridCoordinate last) {

    long x = ((last.getXy().getX()*(numEvents-1)) + currentX)/numEvents;
    long y = ((last.getXy().getY()*(numEvents-1)) + currentY)/numEvents;

    return new Coordinate(x,y);
  }
}
