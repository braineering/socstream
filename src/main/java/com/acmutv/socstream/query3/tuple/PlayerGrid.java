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

package com.acmutv.socstream.query3.tuple;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The tuple representing a set of grid statistics for a single player.
 * @author Giacomo Marciani {@literal <gmarciani@acm.org>}
 * @author Michele Porretta {@literal <mporretta@acm.org>}
 * @since 1.0
 */
@Data
public class PlayerGrid {

  /**
   * The regular expression
   */
  private static final String REGEXP =
      "^(\\d+),(\\d+),(\\d+)$";

  /**
   * The pattern matcher used to match strings on {@code REGEXP}.
   */
  private static final Pattern PATTERN = Pattern.compile(REGEXP);

  /**
   * The player id.
   */
  private long pid;

  /**
   * The grid statistics (gridId,percTime).
   */
  private Map<Long,Double> stats;


  public PlayerGrid(long pid,
                    Map<Long,Double> stats) {
    this.pid = pid;
    this.stats = stats;
  }

  /**
   * Creates an empty sensor event..
   * This constructor is mandatory for Flink serialization.
   */
  public PlayerGrid(){}

  /**
   * Parses {@link PlayerGrid} from string.
   * @param string the string to parse.
   * @return the parsed {@link PlayerGrid}.
   * @throws IllegalArgumentException when {@code string} cannot be parsed.
   */
  public static PlayerGrid valueOf(String string) throws IllegalArgumentException {
    if (string == null) throw new IllegalArgumentException();
    Matcher matcher = PATTERN.matcher(string);
    if (!matcher.matches()) throw new IllegalArgumentException(string);
    long pid = Long.valueOf(matcher.group(1));
    String strStats = matcher.group(2);
    Map<Long,Double> stats = new HashMap<>();
    return new PlayerGrid(pid, stats);
  }

  @Override
  public String toString() {
    return String.format("%d,%s",
        this.pid, this.stats);
  }
}
