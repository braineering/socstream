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

import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The tuple representing a sensor event (limited to position).
 * @author Giacomo Marciani {@literal <gmarciani@acm.org>}
 * @author Michele Porretta {@literal <mporretta@acm.org>}
 * @since 1.0
 */
@Data
public class PositionSensorEvent implements Serializable {

  /**
   * The regular expression
   */
  private static final String REGEXP =
      "^(\\d+),(\\d+),(-?\\d+),(-?\\d+),(-?\\d+)$";

  /**
   * The pattern matcher used to match strings on {@code REGEXP}.
   */
  public static final Pattern PATTERN = Pattern.compile(REGEXP);

  /**
   * The SID or PID.
   */
  private long id;

  /**
   * The timestamp (picoseconds).
   */
  private long ts;

  /**
   * The sensor x-coordinate (mm).
   */
  private long x;

  /**
   * The sensor y-coordinate (mm).
   */
  private long y;

  /**
   * The sensor z-coordinate (mm).
   */
  private long z;


  public PositionSensorEvent(long id, long ts,
                             long x, long y, long z) {
    this.id = id;
    this.ts = ts;
    this.x = x;
    this.y = y;
    this.z = z;
  }

  /**
   * Creates an empty sensor event..
   * This constructor is mandatory for Flink serialization.
   */
  public PositionSensorEvent(){}

  /**
   * Parses {@link PositionSensorEvent} from string.
   * @param string the string to parse.
   * @return the parsed {@link PositionSensorEvent}.
   * @throws IllegalArgumentException when {@code string} cannot be parsed.
   */
  public static PositionSensorEvent valueOf(String string) throws IllegalArgumentException {
    if (string == null) throw new IllegalArgumentException();
    Matcher matcher = PATTERN.matcher(string);
    if (!matcher.matches()) throw new IllegalArgumentException(string);
    long id = Long.valueOf(matcher.group(1));
    long ts = Long.valueOf(matcher.group(2));
    long x = Long.valueOf(matcher.group(3));
    long y = Long.valueOf(matcher.group(4));
    long z = Long.valueOf(matcher.group(5));
    return new PositionSensorEvent(id, ts, x, y, z);
  }

  /**
   * Parses {@link PositionSensorEvent} from string.
   * @param string the string to parse.
   * @return the parsed {@link PositionSensorEvent}.
   * @throws IllegalArgumentException when {@code string} cannot be parsed.
   */
  public static PositionSensorEvent valueOfAsSensorEvent(String string) throws IllegalArgumentException {
    if (string == null) throw new IllegalArgumentException();
    Matcher matcher = RichSensorEvent.PATTERN.matcher(string);
    if (!matcher.matches()) throw new IllegalArgumentException(string);
    long id = Long.valueOf(matcher.group(1));
    long ts = Long.valueOf(matcher.group(2));
    long x = Long.valueOf(matcher.group(3));
    long y = Long.valueOf(matcher.group(4));
    long z = Long.valueOf(matcher.group(5));
    return new PositionSensorEvent(id, ts, x, y, z);
  }

  @Override
  public String toString() {
    return String.format("%d,%d,%d,%d,%d",
        this.id, this.ts, this.x, this.y, this.z);
  }
}
