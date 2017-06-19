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

package com.acmutv.socstream.config;

/**
 * This class encapsulates app details.
 * @author Giacomo Marciani {@literal <gmarciani@acm.org>}
 * @author Michele Porretta {@literal <mporretta@acm.org>}
 * @since 1.0
 */
public class AppManifest {

  /**
   * The app name.
   * CRIMEGRAPH
   */
  public static final String APP_NAME = "SOCSTREAM";

  /**
   * The app version.
   * 1.0
   */
  public static final String APP_VERSION = "1.0";

  /**
   * The app description.
   * Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut
   * labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco
   * laboris nisi ut aliquip ex ea commodo consequat.
   */
  public static final String APP_DESCRIPTION =
      "Soccer analytics leveraging Flink. Solution to DEBS 2013 Grand Challenge." +
          "Coursework in Systems and Architectures for Big Data 2016/2017";

  /**
   * The app team name.
   * ACM Rome Tor Vergata
   */
  public static final String APP_TEAM_NAME = "ACM Rome Tor Vergata";

  /**
   * The app team website.
   * http://acm.uniroma2.it
   */
  public static final String APP_TEAM_URL = "http://acm.uniroma2.it";
}
