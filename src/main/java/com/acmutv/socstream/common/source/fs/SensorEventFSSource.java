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

package com.acmutv.socstream.common.source.fs;

import com.acmutv.socstream.common.tuple.SensorEvent;
import org.apache.flink.streaming.api.functions.source.RichSourceFunction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;

/**
 * A source that reads sensor events from a file.
 * @author Giacomo Marciani {@literal <gmarciani@acm.org>}
 * @author Michele Porretta {@literal <mporretta@acm.org>}
 * @since 1.0
 * @see SensorEvent
 */
public class SensorEventFSSource extends RichSourceFunction<SensorEvent> {

  /**
   * The logger.
   */
  private static final Logger LOG = LoggerFactory.getLogger(SensorEventFSSource.class);

  /**
   * The source status.
   */
  private volatile boolean isRunning = true;

  /**
   * The dataset path.
   */
  private String dataset;

  /**
   * The starting timestamp (events before this will be ignored).
   */
  private Long tsStart;

  /**
   * The ending timestamp (events after this will be ignored).
   */
  private Long tsEnd;

  /**
   * The starting timestamp to ignore (events between this and {@code tsEndIgnore} will be ignored).
   */
  private Long tsStartIgnore;

  /**
   * The ending timestamp to ignore (events between {@code tsStartIgnore} and this will be ignored).
   */
  private Long tsEndIgnore;

  /**
   * The list of sensors id to be ignored.
   */
  private Set<Long> ignoredSensors;

  /**
   * The dataset reader.
   */
  private transient BufferedReader reader;

  /**
   * Creates a new file system source for sensor events.
   * @param dataset the dataset path.
   */
  public SensorEventFSSource(String dataset) {
    this.dataset = dataset;
  }

  /**
   * Creates a new file system source for sensor events with ignoring features.
   * @param dataset the dataset path.
   * @param tsStart the starting timestamp (events before this will be ignored).
   * @param tsEnd the ending timestamp (events after this will be ignored).
   * @param tsStartIgnore the starting timestamp to ignore (events between this and {@code tsEndIgnore} will be ignored).
   * @param tsEndIgnore the ending timestamp to ignore (events between {@code tsStartIgnore} and this will be ignored).
   * @param ignoredSensors the list of sensors id to be ignored.
   */
  public SensorEventFSSource(String dataset,
                             long tsStart, long tsEnd,
                             long tsStartIgnore, long tsEndIgnore,
                             Set<Long> ignoredSensors) {
    this.dataset = dataset;
    this.tsStart = tsStart;
    this.tsEnd = tsEnd;
    this.tsStartIgnore = tsStartIgnore;
    this.tsEndIgnore = tsEndIgnore;
    this.ignoredSensors = ignoredSensors;
  }

  /**
   * Starts the source.
   * @param ctx The context to emit elements to and for accessing locks.
   */
  @Override
  public void run(SourceContext<SensorEvent> ctx) throws Exception {
    Path path = Paths.get(this.dataset);

    this.reader = Files.newBufferedReader(path);

    String line;
    SensorEvent event;
    while ((line = this.reader.readLine()) != null && line.length() != 0) {
      try {
        event = SensorEvent.valueOf(line);
        final long ts = event.getTs();
        if (ts < this.tsStart || ts > this.tsEnd || (ts > tsStartIgnore && ts < tsEndIgnore) ||
            this.ignoredSensors.contains(event.getSid()))
          continue;
        ctx.collect(event);
      } catch (IllegalArgumentException exc) {
        LOG.warn("Malformed link: {}", line);
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
        LOG.error(exc.getMessage());
      } finally {
        this.reader = null;
      }
    }
  }

}
