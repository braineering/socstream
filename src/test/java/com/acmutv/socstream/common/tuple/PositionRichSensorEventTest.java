/*
  The MIT License (MIT)

  Copyright (c) 2016 Giacomo Marciani and Michele Porretta

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

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * JUnit test suite for {@link RichSensorEvent}.
 * @author Giacomo Marciani {@literal <gmarciani@acm.org>}
 * @since 1.0
 * @see RichSensorEvent
 */
public class PositionRichSensorEventTest {

  /**
   * The logger.
   */
  private static final Logger LOG = LoggerFactory.getLogger(PositionRichSensorEventTest.class);

  /**
   * Tests deserialization of {@link PositionSensorEvent} from a string representing a {@link RichSensorEvent}.
   */
  @Test
  public void test_valueOfAsSensorEvent() throws Exception {
    RichSensorEvent sensorEvent = new RichSensorEvent("1",2,3,4,5,6,7,8,9,10,11,12,13);

    PositionSensorEvent actual = PositionSensorEvent.valueOfAsSensorEvent(sensorEvent.toString());

    PositionSensorEvent expected = new PositionSensorEvent("1", 2, 3, 4, 5);

    Assert.assertEquals(expected, actual);
  }

  /**
   * Tests deserialization of {@link PositionSensorEvent}.
   */
  @Test
  public void test_valueOf() throws Exception {

    PositionSensorEvent expected = new PositionSensorEvent("1", 2, 3, 4, 5);

    PositionSensorEvent actual = PositionSensorEvent.valueOf(expected.toString());

    Assert.assertEquals(expected, actual);
  }
}
