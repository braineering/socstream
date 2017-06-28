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

package com.acmutv.socstream.common.meta;

import com.acmutv.socstream.common.tuple.RichSensorEvent;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * JUnit test suite for {@link com.acmutv.socstream.common.meta.MatchService}.
 *
 * @author Giacomo Marciani {@literal <gmarciani@acm.org>}
 * @since 1.0
 * @see RichSensorEvent
 */
public class MatchServiceTest {

  private static final Logger LOG = LoggerFactory.getLogger(MatchServiceTest.class);

  /**
   * Tests deserialization of {@link com.acmutv.socstream.common.meta.Match}.
   */
  @Test
  public void test_deserialize() throws Exception {
    InputStream in = MatchServiceTest.class.getResourceAsStream("/common/meta/metadata.yml");

    Match actual = MatchService.fromYamlFile(in);
    in.close();

    Match expected = new Match();
    expected.getBallsHalf1().add(1L);
    expected.getBallsHalf1().add(2L);
    expected.getBallsHalf2().add(3L);
    expected.getBallsHalf2().add(4L);

    expected.getReferee().setLegLeft(5L);
    expected.getReferee().setLegRight(6L);

    expected.getTeamA().setName("Team-A");
    expected.getTeamA().getPlayers().add(new Person("goalkeeper-A", 7L, 8L, 9L, 10L));
    expected.getTeamA().getPlayers().add(new Person("player-A-1", 11L, 12L, null, null));
    expected.getTeamA().getPlayers().add(new Person("player-A-2", 13L, 14L, null, null));
    expected.getTeamA().getPlayers().add(new Person("player-A-3", 15L, 16L, null, null));

    expected.getTeamB().setName("Team-B");
    expected.getTeamB().getPlayers().add(new Person("goalkeeper-B", 17L, 18L, 19L, 20L));
    expected.getTeamB().getPlayers().add(new Person("player-B-1", 21L, 22L, null, null));
    expected.getTeamB().getPlayers().add(new Person("player-B-2", 23L, 24L, null, null));
    expected.getTeamB().getPlayers().add(new Person("player-B-3", 25L, 26L, null, null));

    Assert.assertEquals(expected, actual);
  }

  /**
   * Tests the collection of sensors to ignore.
   */
  @Test
  public void test_collectIgnoredSensors() throws IOException {
    InputStream in = MatchServiceTest.class.getResourceAsStream("/common/meta/metadata.yml");
    Match match = MatchService.fromYamlFile(in);
    in.close();

    Set<Long> actual = MatchService.collectIgnoredSensors(match);

    Set<Long> expected = new HashSet<>();
    expected.add(1L);
    expected.add(2L);
    expected.add(3L);
    expected.add(4L);
    expected.add(5L);
    expected.add(6L);
    expected.add(9L);
    expected.add(10L);
    expected.add(19L);
    expected.add(20L);

    Assert.assertEquals(expected, actual);
  }

  /**
   * Tests the collection of mapping (SID)->(PID).
   */
  @Test
  public void test_collectSid2Pid() throws IOException {
    InputStream in = MatchServiceTest.class.getResourceAsStream("/common/meta/metadata.yml");
    Match match = MatchService.fromYamlFile(in);
    in.close();

    Map<String,String> actual = MatchService.collectSid2Pid(match);

    Map<String,String> expected = new HashMap<>();
    expected.put("7",  "A0");
    expected.put("8",  "A0");
    expected.put("9",  "A0");
    expected.put("10", "A0");
    expected.put("11", "A1");
    expected.put("12", "A1");
    expected.put("13", "A2");
    expected.put("14", "A2");
    expected.put("15", "A3");
    expected.put("16", "A3");

    expected.put("17", "B0");
    expected.put("18", "B0");
    expected.put("19", "B0");
    expected.put("20", "B0");
    expected.put("21", "B1");
    expected.put("22", "B1");
    expected.put("23", "B2");
    expected.put("24", "B2");
    expected.put("25", "B3");
    expected.put("26", "B3");

    Assert.assertEquals(expected, actual);
  }
}
