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
package com.acmutv.socstream.common.sink.es;

import lombok.Data;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Collection of Elasticsearch properties.
 * @author Giacomo Marciani {@literal <gmarciani@acm.org>}
 * @author Michele Porretta {@literal <mporretta@acm.org>}
 * @since 1.0
 */
@Data
public class ESProperties extends HashMap<String,String> {

  public static final String CLUSTER_NAME = "cluster.name";
  public static final String CLUSTER_NAME_DEFAULT = "my-es-cluster";

  public static final String  BULK_FLUSH_MAX_ACTIONS = "bulk.flush.max.actions";
  public static final String  BULK_FLUSH_MAX_ACTIONS_DEFAULT = "1";

  private List<InetSocketAddress> transportAddresses = new ArrayList<>();

  private String indexName;

  private String typeName;

  public ESProperties(String clusterName, String ...transportAddresses) {
    super();
    super.put(CLUSTER_NAME, clusterName);
    super.put(BULK_FLUSH_MAX_ACTIONS, BULK_FLUSH_MAX_ACTIONS_DEFAULT);
    for (String transportAddress : transportAddresses) {
      String parts[] = transportAddress.split(":");
      try {
        this.transportAddresses.add(new InetSocketAddress(InetAddress.getByName(parts[0]), Integer.valueOf(parts[1])));
      } catch (UnknownHostException exc) {
        exc.printStackTrace();
      }
    }
  }

  public ESProperties(ESProperties other) {
    super(other);
    this.transportAddresses = new ArrayList<>(other.getTransportAddresses());
  }

  public ESProperties() {
    super();
    super.put(CLUSTER_NAME, CLUSTER_NAME_DEFAULT);
    super.put(BULK_FLUSH_MAX_ACTIONS, BULK_FLUSH_MAX_ACTIONS_DEFAULT);
  }

  public static ESProperties fromPropString(String s) throws UnknownHostException {
    ESProperties props = new ESProperties();

    if (s != null) {
      String parts1[] = s.split("@");
      String parts2[] = parts1[1].split(":");
      String parts3[] = parts2[2].split("/");
      props.put(CLUSTER_NAME, parts1[0]);
      props.getTransportAddresses().add(new InetSocketAddress(InetAddress.getByName(parts2[0]), Integer.valueOf(parts2[1])));
      props.indexName = parts3[0];
      props.typeName = parts3[1];
    }

    return props;
  }

  @Override
  public String toString() {
    return String.format("%s,%s,%s,%s",
        this.transportAddresses, this.indexName, this.typeName, super.toString());
  }

}
