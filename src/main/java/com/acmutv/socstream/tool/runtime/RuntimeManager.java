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

package com.acmutv.socstream.tool.runtime;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * This class realizes the app lifecycle services.
 * @author Giacomo Marciani {@literal <gmarciani@acm.org>}
 * @author Michele Porretta {@literal <mporretta@acm.org>}
 * @since 1.0
 */
public class RuntimeManager {

  private static final Logger LOGGER = LoggerFactory.getLogger(RuntimeManager.class);

  /**
   * Registers atexit runnables as JVM shutdown hooks.
   * @param hooks atexit runnables.
   * @see Runtime
   */
  public static void registerShutdownHooks(Runnable ...hooks) {
    Runtime runtime = Runtime.getRuntime();
    for (Runnable hook : hooks) {
      runtime.addShutdownHook(new Thread(hook));
      LOGGER.trace("Registered shutdown hook {}", hook.getClass().getName());
    }
  }

  /**
   * Registers a periodic task.
   * @param task the task to execute.
   * @param delay the delay to first execution.
   * @param period the period between executions.
   * @param timeout the time to interruption.
   * @param unit the time unit.
   */
  private static void registerPeriodic(Runnable task, long delay, long period, long timeout, TimeUnit unit) {
    final ScheduledExecutorService scheduler =
        Executors.newScheduledThreadPool(1);
    final ScheduledFuture<?> handler =
        scheduler.scheduleAtFixedRate(task, delay, period, unit);

    if (timeout > 0) {
      Runnable interrupt = () -> handler.cancel(true);
      scheduler.schedule(interrupt, timeout, TimeUnit.SECONDS);
    }
  }

  /**
   * Executes the given command and arguments.
   * @param command The command to execute. Arguments must be given as a separated strings.
   *                E.g.: BashExecutor.run("ls", "-la") or BashExecutor.run("ls", "-l", "-a")
   * @return The command output as a string.
   * @throws IOException when error in process generation or output.
   */
  public static String run(String ...command) throws IOException {
    LOGGER.trace("command={}", Arrays.asList(command));
    ProcessBuilder pb = new ProcessBuilder(command);
    Process p = pb.start();
    String out = IOUtils.toString(p.getInputStream(), Charset.defaultCharset());
    return out.trim();
  }

  /**
   * Retrieves the local number of cores.
   * @return the number of cores.
   */
  public static int getCores() {
    return Runtime.getRuntime().availableProcessors();
  }

  /**
   * Returns the current JVM name.
   * @return the JVM name.
   */
  public static String getJvmName() {return ManagementFactory.getRuntimeMXBean().getName();}
}
