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

import java.io.Serializable;
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
public class RichSensorEvent extends PositionSensorEvent implements Serializable {

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
   * The sensor acceleration x-coordinate (mm).
   */
  private long ax;

  /**
   * The sensor acceleration y-coordinate (mm).
   */
  private long ay;


  public RichSensorEvent(long id, long ts,
                         long x, long y,
                         long v, long a,
                         long vx, long vy,
                         long ax, long ay) {
    super(id, ts, x, y);
    this.v = v;
    this.a = a;
    this.vx = vx;
    this.vy = vy;
    this.ax = ax;
    this.ay = ay;
  }

  /**
   * Creates an empty sensor event..
   * This constructor is mandatory for Flink serialization.
   */
  public RichSensorEvent(){
    super();
  }

  public static RichSensorEvent fromDataset(String string) throws IllegalArgumentException {
    String fields[] = string.split(",");
    if (fields.length != 13) {
      throw new IllegalArgumentException();
    }
    long id = Long.valueOf(fields[0]);
    long ts = Long.valueOf(fields[1]);
    long x = Long.valueOf(fields[2]);
    long y = Long.valueOf(fields[3]);
    long v = Long.valueOf(fields[5]);
    long a = Long.valueOf(fields[6]);
    long vx = Long.valueOf(fields[7]);
    long vy = Long.valueOf(fields[8]);
    long ax = Long.valueOf(fields[10]);
    long ay = Long.valueOf(fields[11]);
    return new RichSensorEvent(id, ts, x, y, v, a, vx, vy, ax, ay);
  }


  @Override
  public String toString() {
    return String.format("%d,%d,%d,%d,%d,%d,%d,%d,%d,%d",
        super.getId(), super.getTs(), super.getX(), super.getY(),
        this.v, this.a,
        this.vx, this.vy,
        this.ax, this.ay);
  }
}
