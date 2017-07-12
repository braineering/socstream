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

package com.acmutv.socstream.core.query2.tuple;

import com.acmutv.socstream.query2.tuple.RankingElement;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * JUnit test suite for {@link com.acmutv.socstream.query2.tuple.RankingElement}.
 * @author Giacomo Marciani {@literal <gmarciani@acm.org>}
 * @author Michele Porretta {@literal <mporretta@acm.org>}
 * @since 1.0
 * @see RankingElement
 */
public class RankingElementTest {

  /**
   * The logger.
   */
  private static final Logger LOG = LoggerFactory.getLogger(RankingElementTest.class);

  /**
   * Tests serialization/deserialization of {@link RankingElement}.
   */
  @Test
  public void test_serialize() throws Exception {
    List<RankingElement> elems = new ArrayList<>();
    elems.add(new RankingElement(1,1.0));

    for (RankingElement expected : elems) {
      LOG.debug("RankingElement serialized: " + expected);
      String str = expected.toString();
      RankingElement actual = RankingElement.valueOf(str);
      Assert.assertEquals(expected, actual);
    }
  }
}
