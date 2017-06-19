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

import com.acmutv.socstream.common.ProgramDriver;
import com.acmutv.socstream.query1.SocstreamQuery1;
import com.acmutv.socstream.tool.runtime.RuntimeManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The app entry-point as programs driver.
 * @author Giacomo Marciani {@literal <gmarciani@acm.org>}
 * @author Michele Porretta {@literal <mporretta@acm.org>}
 * @since 1.0
 * @see RuntimeManager
 */
public class SocstreamDriver {

  /**
   * The logger.
   */
  private static final Logger LOG = LoggerFactory.getLogger(SocstreamDriver.class);


  /**
   * Thedriver main method.
   * @param args the command line arguments.
   */
  public static void main(String[] args) throws Exception {
    int exitCode = -1;
    ProgramDriver driver = new ProgramDriver();

    try {
    /* *********************************************************************************************
     * QUERY 1
     **********************************************************************************************/
      driver.addClass(SocstreamQuery1.PROGRAM_NAME, SocstreamQuery1.class, SocstreamQuery1.PROGRAM_DESCRIPTION);

    /* *********************************************************************************************
     * QUERY 2
     **********************************************************************************************/
      driver.addClass(SocstreamQuery1.PROGRAM_NAME, SocstreamQuery1.class, SocstreamQuery1.PROGRAM_DESCRIPTION);

    /* *********************************************************************************************
     * QUERY 3
     **********************************************************************************************/
      driver.addClass(SocstreamQuery1.PROGRAM_NAME, SocstreamQuery1.class, SocstreamQuery1.PROGRAM_DESCRIPTION);

      LOG.info("Running driver...");

      exitCode = driver.run(args);

    } catch (Throwable exc) {
      exc.printStackTrace();
      LOG.error(exc.getMessage());
    }

    System.exit(exitCode);
  }

}
