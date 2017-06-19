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

import java.util.ArrayList;
import java.util.List;

/**
 * JUnit test suite for {@link NodePair}.
 * @author Giacomo Marciani {@literal <gmarciani@acm.org>}
 * @author Michele Porretta {@literal <mporretta@acm.org>}
 * @since 1.0
 * @see Link
 */
public class NodePairTest {

  private static final Logger LOGGER = LoggerFactory.getLogger(NodePairTest.class);

  /**
   * Tests serialization of {@link NodePair}.
   */
  @Test
  public void test_serialize() throws Exception {
    List<NodePair>  data = new ArrayList<>();
    data.add(new NodePair(1,2,UpdateType.BOTH));
    data.add(new NodePair(2,3,UpdateType.BOTH));
    data.add(new NodePair(3,4,UpdateType.BOTH));
    data.add(new NodePair(4,5,UpdateType.BOTH));

    for (NodePair expected : data) {
      LOGGER.debug("NodePair serialized: " + expected);
      String str = expected.toString();
      NodePair actual = NodePair.valueOf(str);
      Assert.assertEquals(expected, actual);
    }
  }
}
