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

import javafx.util.Pair;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The tuple representing the occupation of the game's field for one player
 * @author Giacomo Marciani {@literal <gmarciani@acm.org>}
 * @author Michele Porretta {@literal <mporretta@acm.org>}
 * @since 1.0
 */
@Data
public class PlayerOccupation {

  /**
   * The regular expression for generic PlayerOccupatio
   */
  private static final String REGEXP =
      "^(\\d+),(\\d+)((:?,(\\d+;\\d+),(\\d+(?:\\.\\d+))){0,104})$";

  /**
   * The pattern matcher used to match strings on {@code REGEXP}.
   */
  private static final Pattern PATTERN = Pattern.compile(REGEXP);

  /**
   * The timestamp of start analysis for that player.
   */
  private long ts;

  /**
   * The player id.
   */
  private long pid;

  /**
   *
   */
  private Map<Pair<Long,Long>,Double> cellsusage;


  public PlayerOccupation(long ts, long pid, Map<Pair<Long,Long>,Double> cells) {
    this.ts = ts;
    this.pid = pid;
    this.cellsusage = cells;
  }

  /**
   * Creates an empty PlayerOccupation
   * This constructor is mandatory for Flink serialization.
   */
  public PlayerOccupation(){
  }

  /**
   * Parses {@link PlayerOccupation} from string.
   * @param string the string to parse.
   * @return the parsed {@link PlayerOccupation}.
   * @throws IllegalArgumentException when {@code string} cannot be parsed.
   */
  public static PlayerOccupation valueOf(String string) throws IllegalArgumentException {
    if (string == null) throw new IllegalArgumentException();
    Matcher matcher = PATTERN.matcher(string);
    if (!matcher.matches()) throw new IllegalArgumentException(string);
    long ts = Long.valueOf(matcher.group(1));
    long pid = Long.valueOf(matcher.group(2));

    Map<Pair<Long,Long>,Double> cells = new HashMap<>();

    String groupOfCell = String.valueOf(matcher.group(3)).substring(1);
    String[] cellWithValues = groupOfCell.split(",");

    for(int i = 0; i <cellWithValues.length; i+=2) {
      String[] cellId = cellWithValues[i].toString().split(";");
      Pair<Long, Long> coordinate = new Pair<>(Long.valueOf(cellId[0]), Long.valueOf(cellId[1]));
      Double percentage = Double.valueOf(cellWithValues[i + 1].toString());
      cells.put(coordinate,percentage);
    }

    return new PlayerOccupation(ts,pid,cells);
  }

  public String printCellOccupation(){
    String occupation ="";
    String data = this.cellsusage.toString();
    int length = data.length();
    String[] cells = data.substring(1,length-1).split(",");

    for(int i=0; i<cells.length;i++){
      String[] cell = cells[i].split("=");
      occupation += "," + cell[0] + ";" + cell[1] + ","+cell[2];
    }
    occupation = occupation.replace(" ","");
    return occupation;
  }

  @Override
  public String toString() {
    return String.format("%d,%d%s",
        this.ts, this.pid,printCellOccupation());
  }
}
