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

package com.acmutv.socstream;

import com.acmutv.socstream.common.tuple.NodePairScores;
import com.acmutv.socstream.common.tuple.ScoreType;
import com.acmutv.socstream.common.tuple.UpdateType;
import org.junit.Assert;
import org.junit.Test;

import java.io.*;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static com.acmutv.socstream.common.tuple.ScoreType.CN;
import static com.acmutv.socstream.common.tuple.ScoreType.NTA;
import static com.acmutv.socstream.common.tuple.ScoreType.TA;
import static com.acmutv.socstream.common.tuple.UpdateType.ALL;
import static com.acmutv.socstream.common.tuple.UpdateType.TM;

/**
 * Miscellanea JUnit tests (for personal use only)
 * @author Giacomo Marciani {@literal <gmarciani@acm.org>}
 * @since 1.0
 */
public class Misc {

  @Test
  public void test() throws IOException {
    NodePairScores scores = new NodePairScores(1, 2);
    Assert.assertTrue(checkMalformed(ALL, scores));
    Assert.assertTrue(checkMalformed(TM, scores));
    //
    scores.addScore(NTA, 1.0);
    Assert.assertTrue(checkMalformed(ALL, scores));
    Assert.assertTrue(checkMalformed(TM, scores));

    scores.addScore(TA, 1.0);
    Assert.assertTrue(checkMalformed(ALL, scores));
    Assert.assertFalse(checkMalformed(TM, scores));

    scores.addScore(CN, 1.0);
    Assert.assertTrue(checkMalformed(ALL, scores));
    Assert.assertTrue(checkMalformed(TM, scores));

    for (ScoreType scoreType : ScoreType.values()) {
      scores.addScore(scoreType, 1.0);
    }

    Assert.assertFalse(checkMalformed(ALL, scores));
    Assert.assertTrue(checkMalformed(TM, scores));
  }


  Set<ScoreType> tm = new HashSet<ScoreType>(){{add(TA);add(NTA);}};
  Set<ScoreType> all = new HashSet<ScoreType>(){{addAll(Arrays.asList(ScoreType.values()));}};
  private boolean checkMalformed(UpdateType updateType, NodePairScores scores) {
    if (
        (ALL.equals(updateType) && !scores.f2.keySet().equals(all))
            ||
            (TM.equals(updateType) && ! scores.f2.keySet().equals(tm))
        ) {
      return true;
    }
    return false;
  }
}
