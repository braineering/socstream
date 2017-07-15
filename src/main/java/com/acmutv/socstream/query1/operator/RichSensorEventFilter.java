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
package com.acmutv.socstream.query1.operator;

import com.acmutv.socstream.common.tuple.RichSensorEvent;
import com.acmutv.socstream.common.tuple.RichSensorEvent2;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.flink.api.common.functions.FilterFunction;

import java.util.Map;
import java.util.Set;

/**
 * This class realizes ...
 *
 * @author Giacomo Marciani {@literal <gmarciani@acm.org>}
 * @author Michele Porretta {@literal <mporretta@acm.org>}
 * @since 1.0
 */
@Data
@AllArgsConstructor
public class RichSensorEventFilter implements FilterFunction<RichSensorEvent2> {

  /**
   * The starting timestamp (events before this will be ignored).
   */
  private long tsStart;

  /**
   * The ending timestamp (events after this will be ignored).
   */
  private long tsEnd;

  /**
   * The starting timestamp to ignore (events between this and {@code tsEndIgnore} will be ignored).
   */
  private long tsStartIgnore;

  /**
   * The ending timestamp to ignore (events between {@code tsStartIgnore} and this will be ignored).
   */
  private long tsEndIgnore;

  /**
   * The ignore list for sensors id.
   */
  private Set<Long> ignoredSensors;

  /**
   * The filter function that evaluates the predicate.
   * <p>
   * <strong>IMPORTANT:</strong> The system assumes that the function does not
   * modify the elements on which the predicate is applied. Violating this assumption
   * can lead to incorrect results.
   *
   * @param event The value to be filtered.
   * @return True for values that should be retained, false for values to be filtered out.
   * @throws Exception This method may throw exceptions. Throwing an exception will cause the operation
   *                   to fail and may trigger recovery.
   */
  @Override
  public boolean filter(RichSensorEvent2 event) throws Exception {
    if (this.ignoredSensors.contains(event.getId())) {
      //LOG.debug("Ignored sensor event (untracked SID): {}", strEvent);
      return false;
    }

    final long ts = event.getTs();

    return !(ts < this.tsStart || (ts > tsStartIgnore && ts < tsEndIgnore) || ts > this.tsEnd);
  }
}
