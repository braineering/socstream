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

package com.acmutv.socstream.common.source.meta.serial;

import com.acmutv.socstream.common.source.meta.*;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.util.Iterator;

/**
 * This class realizes the JSON deserializer for {@link Match}.
 * @author Giacomo Marciani {@literal <gmarciani@acm.org>}
 * @author Michele Porretta {@literal <mporretta@acm.org>}
 * @since 1.0
 * @see Match
 */
public class MatchMetadataConfigurationDeserializer extends StdDeserializer<Match> {

  /**
   * The singleton of {@link MatchMetadataConfigurationDeserializer}.
   */
  private static MatchMetadataConfigurationDeserializer instance;

  /**
   * Returns the singleton of {@link MatchMetadataConfigurationDeserializer}.
   * @return the singleton.
   */
  public static MatchMetadataConfigurationDeserializer getInstance() {
    if (instance == null) {
      instance = new MatchMetadataConfigurationDeserializer();
    }
    return instance;
  }

  /**
   * Initializes the singleton of {@link MatchMetadataConfigurationDeserializer}.
   */
  private MatchMetadataConfigurationDeserializer() {
    super((Class<?>)null);
  }

  @Override
  public Match deserialize(JsonParser parser, DeserializationContext ctx) throws IOException {
    Match match = new Match();
    JsonNode node = parser.getCodec().readTree(parser);

    if (!node.hasNonNull("ball") || !node.hasNonNull("referee") ||
        !node.hasNonNull("teamA") || !node.hasNonNull("teamB")) {
      throw new IOException("Malformed match metadata (missing fields)");
    }

    if (node.get("ball").hasNonNull("half.1")) {
      match.getBallsHalf1().clear();
      Iterator<JsonNode> iter = node.get("half.1").elements();
      while (iter.hasNext()) {
        long v = iter.next().asLong();
        match.getBallsHalf1().add(v);
      }
    }

    if (node.get("ball").hasNonNull("half.2")) {
      match.getBallsHalf1().clear();
      Iterator<JsonNode> iter = node.get("half.2").elements();
      while (iter.hasNext()) {
        long v = iter.next().asLong();
        match.getBallsHalf1().add(v);
      }
    }



    if (node.hasNonNull("referee")) {
      if (node.hasNonNull("leg.left")) {
        long refereeLegLeft = node.get("leg.left").asLong();
        match.getReferee().setLegLeft(refereeLegLeft);
      }
      if (node.hasNonNull("leg.right")) {
        long refereeLegRight = node.get("leg.right").asLong();
        match.getReferee().setLegRight(refereeLegRight);
      }
    }

    if (node.hasNonNull("teamA")) {
      final String teamName = node.get("teamA").get("name").asText();
      Team team = new Team(teamName);
      Iterator<JsonNode> iterPlayers = node.get("teamA").get("players").elements();
      boolean first = true;
      while (iterPlayers.hasNext()) {
        JsonNode n = iterPlayers.next();
        final String playerName = n.get("name").asText();
        final long legLeft = n.get("leg.left").asLong(-1);
        final long legRight = n.get("leg.right").asLong(-1);
        final long armLeft = n.get("arm.left").asLong(-1);
        final long armRight = n.get("arm.right").asLong(-1);
        Player player = new Player(playerName, legLeft, legRight, armLeft, armRight);
        if (first) {
          team.setGoalkeeper(player);
          first = false;
        } else {
          team.getPlayers().add(player);
        }
      }
      match.setTeamA(team);
    }

    if (node.hasNonNull("teamB")) {
      final String teamName = node.get("teamB").get("name").asText();
      Team team = new Team(teamName);
      Iterator<JsonNode> iterPlayers = node.get("teamA").get("players").elements();
      boolean first = true;
      while (iterPlayers.hasNext()) {
        JsonNode n = iterPlayers.next();
        final String playerName = n.get("name").asText();
        final long legLeft = n.get("leg.left").asLong(-1);
        final long legRight = n.get("leg.right").asLong(-1);
        final long armLeft = n.get("arm.left").asLong(-1);
        final long armRight = n.get("arm.right").asLong(-1);
        Player player = new Player(playerName, legLeft, legRight, armLeft, armRight);
        if (first) {
          team.setGoalkeeper(player);
          first = false;
        } else {
          team.getPlayers().add(player);
        }
      }
      match.setTeamA(team);
    }

    return match;
  }
}
