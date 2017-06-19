/*
  The MIT License (MIT)

  Copyright (c) 2017 Giacomo Marciani

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
package com.acmutv.socstream.common.source;

import lombok.*;

import java.util.Properties;

/**
 * Collection of Kafka properties.
 * @author Giacomo Marciani {@literal <gmarciani@acm.org>}
 * @since 1.0
 */
@Getter
@NoArgsConstructor
public class KafkaProperties extends Properties {

  public static final String BOOTSTRAP_SERVERS = "bootstrap.servers";

  public static final String ZOOKEEPER_CONNECT = "zookeeper.connect";

  public static final String GROUP_ID = "group.id";

  public KafkaProperties(String bootstrapServers, String zookeeperConnect, String groupId) {
    super();
    super.put(BOOTSTRAP_SERVERS, bootstrapServers);
    super.put(ZOOKEEPER_CONNECT, zookeeperConnect);
    super.put(GROUP_ID, groupId);
  }

  public KafkaProperties(KafkaProperties other) {
    super(other);
  }

  public void setBootstrapServers(String bootstrapServers) {
    super.put(BOOTSTRAP_SERVERS, bootstrapServers);
  }

  public void setZookeeperConnect(String zookeeperConnect) {
    super.put(ZOOKEEPER_CONNECT, zookeeperConnect);
  }

  public void setGroupId(String groupId) {
    super.put(GROUP_ID, groupId);
  }

}
