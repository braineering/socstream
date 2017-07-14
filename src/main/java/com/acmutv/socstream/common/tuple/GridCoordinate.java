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

package com.acmutv.socstream.common.tuple;

import lombok.Data;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The tuple representing a couple of indices x,y to indicate a coordinate(x,y)
 * @author Giacomo Marciani {@literal <gmarciani@acm.org>}
 * @author Michele Porretta {@literal <mporretta@acm.org>}
 * @since 1.0
 */
@Data
public class GridCoordinate {

  /**
   * The regular expression
   */
  private static final String REGEXP =
      "^(\\d+);(\\d+),(\\d+),(\\d+)$";

  /**
   * The pattern matcher used to match strings on {@code REGEXP}.
   */
  private static final Pattern PATTERN = Pattern.compile(REGEXP);

  /**
   * The sensor x-coordinate (mm).
   */
  private long x;

  /**
   * The sensor y-coordinate (mm).
   */
  private long y;

  /**
   *
   */
  private Coordinate xy;

  /**
   * The coordinateID
   */
  private String ID;


  public GridCoordinate(long x, long y, Coordinate xy) {
    this.x = x;
    this.y = y;
    this.xy = xy;
    this.ID = Long.toString(x)+";"+Long.toString(y);
  }

  public String getKey(){
    return ID;
  }

  /**
   * Creates an empty sensor event..
   * This constructor is mandatory for Flink serialization.
   */
  public GridCoordinate(){}

  /**
   * Parses {@link GridCoordinate} from string.
   * @param string the string to parse.
   * @return the parsed {@link GridCoordinate}.
   * @throws IllegalArgumentException when {@code string} cannot be parsed.
   */
  public static GridCoordinate valueOf(String string) throws IllegalArgumentException {
    if (string == null) throw new IllegalArgumentException();
    Matcher matcher = PATTERN.matcher(string);
    if (!matcher.matches()) throw new IllegalArgumentException(string);
    long x = Long.valueOf(matcher.group(1));
    long y = Long.valueOf(matcher.group(2));
    Coordinate xy = Coordinate.valueOf(matcher.group(3)+","+matcher.group(4));
    return new GridCoordinate(x, y, xy);
  }

  @Override
  public String toString() {
    return String.format("%d;%d",
        this.x, this.y);
  }

  @Override
  public boolean equals(Object obj){
    if(!(obj instanceof  GridCoordinate)){
      return false;
    }
    GridCoordinate gc = (GridCoordinate) obj;

    return this.x == gc.getX() && this.y == gc.getY();
  }
}
