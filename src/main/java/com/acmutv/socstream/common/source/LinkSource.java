/*
  The MIT License (MIT)

  Copyright (c) 2017 Giacomo Marciani and Michele Porretta

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

import com.acmutv.socstream.common.tuple.Link;
import org.apache.flink.streaming.api.functions.source.RichSourceFunction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * A source that reads links from a file.
 * @author Giacomo Marciani {@literal <gmarciani@acm.org>}
 * @author Michele Porretta {@literal <mporretta@acm.org>}
 * @since 1.0
 * @see Link
 */
public class LinkSource extends RichSourceFunction<Link> {

  private static final Logger LOGGER = LoggerFactory.getLogger(LinkSource.class);

  private volatile boolean isRunning = true;

  /**
   * The dataset path.
   */
  private String dataset;

  /**
   * The dataset reader.
   */
  private transient BufferedReader reader;

  /**
   * Creates a new link source.
   * @param dataset the dataset path.
   */
  public LinkSource(String dataset) {
    this.dataset = dataset;
  }

  /**
   * Starts the source.
   * @param ctx The context to emit elements to and for accessing locks.
   */
  @Override
  public void run(SourceContext<Link> ctx) throws Exception {
    Path path = Paths.get(this.dataset);

    this.reader = Files.newBufferedReader(path);

    String line;
    Link link;
    while ((line = this.reader.readLine()) != null && line.length() != 0) {
      try {
        link = Link.valueOf(line);
        ctx.collect(link);
      } catch (IllegalArgumentException exc) {
        LOGGER.warn("Malformed link: {}", line);
      }
    }

    this.reader.close();
  }

  /**
   * Cancels the source. Most sources will have a while loop inside the
   * {@link #run(SourceContext)} method. The implementation needs to ensure that the
   * source will break out of that loop after this method is called.
   * <p>
   * <p>A typical pattern is to have an {@code "volatile boolean isRunning"} flag that is set to
   * {@code false} in this method. That flag is checked in the loop condition.
   * <p>
   * <p>When a source is canceled, the executing thread will also be interrupted
   * (via {@link Thread#interrupt()}). The interruption happens strictly after this
   * method has been called, so any interruption handler can rely on the fact that
   * this method has completed. It is good practice to make any flags altered by
   * this method "volatile", in order to guarantee the visibility of the effects of
   * this method to any interruption handler.
   */
  @Override
  public void cancel() {
    this.isRunning = false;
    if (this.reader != null) {
      try {
        this.reader.close();
      } catch (IOException exc) {
        LOGGER.error(exc.getMessage());
      } finally {
        this.reader = null;
      }
    }
  }

}
