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

package com.acmutv.socstream.tool;

import com.acmutv.socstream.common.tuple.Link;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Utility for message publishing to Kafka sources.
 * @author Giacomo Marciani {@literal <gmarciani@acm.org>}
 * @author Michele Porretta {@literal <mporretta@acm.org>}
 * @since 1.0
 * @see Link
 */
public class DatasetFormatter {

  private static final Logger LOGGER = LoggerFactory.getLogger(DatasetFormatter.class);

  /**
   * Produces links from file and push on main-topic
   */
  public static void main(String[] args) throws Exception {

    Path path = FileSystems.getDefault().getPath("data/facebook_friendships.data");

    BufferedReader reader = Files.newBufferedReader(path);
    List<Link> data = new ArrayList<>();

    while (reader.ready()) {
      String[] tokenizer = reader.readLine().toString().split("\\s+");
      Link link = Link.valueOf("("+tokenizer[0]+","+tokenizer[1]+",1.0)");
      data.add(link);
    }

    Path destination = FileSystems.getDefault().getPath("data/facebook_friendshipsnew.data");

    if (!Files.isDirectory(path.getParent())) {
      Files.createDirectories(path.getParent());
    }

    writeDataset(destination,data);
    System.out.println("Operazione eseguita");

  }

  /**
   * Writes the dataset {@code data} into {@code path}.
   * @param path the file to write onto.
   * @param data the dataset to write.
   */
  private static void writeDataset(Path path, List<Link> data) {
    Charset charset = Charset.defaultCharset();
    try (BufferedWriter writer = Files.newBufferedWriter(path, charset)) {
      for (Link link : data) {
        writer.append(link.toString()).append("\n");
      }
    } catch (IOException exc) {
      LOGGER.error(exc.getMessage());
    }
  }
}
