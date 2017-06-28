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
import lombok.EqualsAndHashCode;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The tuple representing a sensor event.
 * @author Giacomo Marciani {@literal <gmarciani@acm.org>}
 * @author Michele Porretta {@literal <mporretta@acm.org>}
 * @since 1.0
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class RichSensorEvent extends PositionSensorEvent {

  /**
   * The regular expression
   */
  private static final String REGEXP =
      "^([a-zA-Z0-9]+),(\\d+),(\\d+),(\\d+),(\\d+),(\\d+),(\\d+),(\\d+),(\\d+),(\\d+),(\\d+),(\\d+),(\\d+)$";

  /**
   * The pattern matcher used to match strings on {@code REGEXP}.
   */
  public static final Pattern PATTERN = Pattern.compile(REGEXP);

  /**
   * The sensor speed magnitude (um/s).
   */
  private long v;

  /**
   * The sensor acceleration magnitude (um/s).
   */
  private long a;

  /**
   * The sensor speed x-coordinate (mm).
   */
  private long vx;

  /**
   * The sensor speed y-coordinate (mm).
   */
  private long vy;

  /**
   * The sensor speed z-coordinate (mm).
   */
  private long vz;

  /**
   * The sensor acceleration x-coordinate (mm).
   */
  private long ax;

  /**
   * The sensor acceleration y-coordinate (mm).
   */
  private long ay;

  /**
   * The sensor acceleration z-coordinate (mm).
   */
  private long az;


  public RichSensorEvent(String id, long ts,
                         long x, long y, long z,
                         long v, long a,
                         long vx, long vy, long vz,
                         long ax, long ay, long az) {
    super(id, ts, x, y, z);
    this.v = v;
    this.a = a;
    this.vx = vx;
    this.vy = vy;
    this.vz = vz;
    this.ax = ax;
    this.ay = ay;
    this.az = az;
  }

  /**
   * Creates an empty sensor event..
   * This constructor is mandatory for Flink serialization.
   */
  public RichSensorEvent(){
    super();
  }

  /**
   * Parses {@link RichSensorEvent} from string.
   * @param string the string to parse.
   * @return the parsed {@link RichSensorEvent}.
   * @throws IllegalArgumentException when {@code string} cannot be parsed.
   */
  public static RichSensorEvent valueOf(String string) throws IllegalArgumentException {
    if (string == null) throw new IllegalArgumentException();
    Matcher matcher = PATTERN.matcher(string);
    if (!matcher.matches()) throw new IllegalArgumentException(string);
    String id = matcher.group(1);
    long ts = Long.valueOf(matcher.group(2));
    long x = Long.valueOf(matcher.group(3));
    long y = Long.valueOf(matcher.group(4));
    long z = Long.valueOf(matcher.group(5));
    long v = Long.valueOf(matcher.group(6));
    long a = Long.valueOf(matcher.group(7));
    long vx = Long.valueOf(matcher.group(8));
    long vy = Long.valueOf(matcher.group(9));
    long vz = Long.valueOf(matcher.group(10));
    long ax = Long.valueOf(matcher.group(11));
    long ay = Long.valueOf(matcher.group(12));
    long az = Long.valueOf(matcher.group(13));
    return new RichSensorEvent(id, ts, x, y, z, v, a, vx, vy, vz, ax, ay, az);
  }

  @Override
  public String toString() {
    return String.format("%s,%d,%d,%d,%d,%d,%d,%d,%d,%d,%d,%d,%d",
        super.getId(), super.getTs(), super.getX(), super.getY(), super.getZ(),
        this.v, this.a,
        this.vx, this.vy, this.vz,
        this.ax, this.ay, this.az);
  }
}
