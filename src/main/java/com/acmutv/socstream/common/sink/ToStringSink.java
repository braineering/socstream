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

package com.acmutv.socstream.common.sink;

import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.functions.sink.RichSinkFunction;

import javax.annotation.Nonnull;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * A sink operator that writes tuples to a specific file.
 * @author Giacomo Marciani {@literal <gmarciani@acm.org>}
 * @author Michele Porretta {@literal <mporretta@acm.org>}
 * @since 1.0
 */
public class ToStringSink<T> extends RichSinkFunction<T> {

  /**
   * The output file path.
   */
  private String path;

  /**
   * The writer to the output file.
   */
  private BufferedWriter writer;

  /**
   * Creates a new sink.
   * @param path the output file path.
   * @throws IOException when file cannot be written.
   */
  public ToStringSink(@Nonnull final String path) throws IOException {
    this.path = path;
  }

  @Override
  public void open(Configuration conf) throws IOException {
    Path path = Paths.get(this.path);
    if (!Files.exists(path)) {
      Files.createFile(path);
    }
    this.writer = Files.newBufferedWriter(path, Charset.defaultCharset());
  }

  @Override
  public void close() throws IOException {
    this.writer.flush();
    this.writer.close();
  }

  @Override
  public void invoke(T elem) throws Exception {
    this.writer.write(elem.toString());
    this.writer.newLine();
  }
}
