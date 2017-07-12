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

package com.acmutv.socstream.query1.tuple;

import lombok.Data;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The tuple representing a set of running statistics.
 * @author Giacomo Marciani {@literal <gmarciani@acm.org>}
 * @author Michele Porretta {@literal <mporretta@acm.org>}
 * @since 1.0
 */
@Data
public class PlayerRunningStatistics {

  /**
   * The regular expression
   */
  private static final String REGEXP =
      "^(\\d+),(\\d+),(\\d+),(\\d*\\.?\\d+),(\\d*\\.?\\d+)$";

  /**
   * The pattern matcher used to match strings on {@code REGEXP}.
   */
  private static final Pattern PATTERN = Pattern.compile(REGEXP);

  /**
   * The timestamp (start instant) in picoseconds.
   */
  private long tsStart;

  /**
   * The timestamp (stop instant) in picoseconds.
   */
  private long tsStop;

  /**
   * The player id.
   */
  private long pid;

  /**
   * The total distance traveled by the player (m).
   */
  private double dist;

  /**
   * The average speed of the player (m/s).
   */
  private double avgSpeed;

  /**
   * Creates a new set of runnign statistics for a player.
   * @param tsStart the start timestamp.
   * @param tsStop the stop timestamp.
   * @param pid the player id (PID).
   * @param avgSpeed the average speed (m/s).
   * @param dist the average distance (m).
   */
  public PlayerRunningStatistics(long tsStart, long tsStop,
                                 long pid, double dist, double avgSpeed) {
    this.tsStart = tsStart;
    this.tsStop = tsStop;
    this.pid = pid;
    this.dist = dist;
    this.avgSpeed = avgSpeed;
  }

  /**
   * Creates an empty sensor event..
   * This constructor is mandatory for Flink serialization.
   */
  public PlayerRunningStatistics(){}

  /**
   * Parses {@link PlayerRunningStatistics} from string.
   * @param string the string to parse.
   * @return the parsed {@link PlayerRunningStatistics}.
   * @throws IllegalArgumentException when {@code string} cannot be parsed.
   */
  public static PlayerRunningStatistics valueOf(String string) throws IllegalArgumentException {
    if (string == null) throw new IllegalArgumentException();
    Matcher matcher = PATTERN.matcher(string);
    if (!matcher.matches()) throw new IllegalArgumentException(string);
    long tsStart = Long.valueOf(matcher.group(1));
    long tsStop = Long.valueOf(matcher.group(2));
    long pid = Long.valueOf(matcher.group(3));
    long dist = Long.valueOf(matcher.group(4));
    long avgSpeed = Long.valueOf(matcher.group(5));
    return new PlayerRunningStatistics(tsStart, tsStop, pid, dist, avgSpeed);
  }

  @Override
  public String toString() {
    return String.format("%d,%d,%d,%f,%f",
        this.tsStart, this.tsStop, this.pid, this.dist, this.avgSpeed);
  }
}
