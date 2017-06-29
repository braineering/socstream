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

import com.acmutv.socstream.query3.tuple.PlayerOccupation;
import org.junit.Assert;
import org.junit.Test;

/**
 * JUnit test suite for {@link PlayerOccupation}.
 * @author Giacomo Marciani {@literal <gmarciani@acm.org>}
 * @author Michele Porretta {@literal <mporretta@acm.org>}
 * @since 1.0
 * @see PlayerOccupation
 */
public class PlayerOccupationTest {

  //private static final Logger LOGGER = LoggerFactory.getLogger(PlayerOccupationTest.class);

  /**
   * Tests serialization of {@link PlayerOccupation}.
   */
  @Test
  public void test_serialize_occupation() throws Exception {
    String expected ="63,64,0;0,0.0,0;1,10.0,0;2,20.0";
    PlayerOccupation actual = PlayerOccupation.valueOf(expected);
    Assert.assertEquals(expected, actual.toString());
  }
}
