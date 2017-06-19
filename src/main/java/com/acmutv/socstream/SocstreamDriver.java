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

import com.acmutv.socstream.config.AppConfiguration;
import com.acmutv.socstream.config.AppConfigurationService;
import com.acmutv.socstream.config.serial.AppConfigurationYamlMapper;
import com.acmutv.socstream.common.db.DbConfiguration;
import com.acmutv.socstream.common.source.SourceType;
import com.acmutv.socstream.query1.SocstreamQuery1;
import com.acmutv.socstream.query2.SocstreamQuery2;
import com.acmutv.socstream.query3.SocstreamQuery3;
import com.acmutv.socstream.tool.runtime.RuntimeManager;
import com.acmutv.socstream.ui.CliService;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

/**
 * The app entry-point for the application.
 * Before starting the application, it is necessary to open the socket, running
 * {@code $> ncat 127.0.0.1 9000 -l}
 * and start typing tuples.
 * @author Giacomo Marciani {@literal <gmarciani@acm.org>}
 * @author Michele Porretta {@literal <mporretta@acm.org>}
 * @since 1.0
 * @see AppConfigurationService
 * @see RuntimeManager
 */
public class SocstreamDriver {


  /**
   * The app main method, executed when the program is launched.
   * @param args the command line arguments.
   */
  public static void main(String[] args) throws Exception {

    String queryName = args[0];

    if ("query-1".equals(queryName)) {
      SocstreamQuery1.main(args);
    } else if ("query-2".equals(queryName)) {
      SocstreamQuery2.main(args);
    } else if ("query-3".equals(queryName)) {
      SocstreamQuery3.main(args);
    }
  }

}
