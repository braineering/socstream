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


  public PositionSensorEvent(long id, long ts,
                             long x, long y) {
    this.id = id;
    this.ts = ts;
    this.x = x;
    this.y = y;
  }

  /**
   * Creates an empty sensor event..
   * This constructor is mandatory for Flink serialization.
   */
  public PositionSensorEvent(){}

  public static PositionSensorEvent fromDataset(String string) throws IllegalArgumentException {
    String fields[] = string.split(",");
    if (fields.length != 13) {
      throw new IllegalArgumentException();
    }
    long id = Long.valueOf(fields[0]);
    long ts = Long.valueOf(fields[1]);
    long x = Long.valueOf(fields[2]);
    long y = Long.valueOf(fields[3]);
    return new PositionSensorEvent(id, ts, x, y);
  }

  @Override
  public String toString() {
    return String.format("%d,%d,%d,%d",
        this.id, this.ts, this.x, this.y);
  }
}
