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

package com.acmutv.socstream.tool.io;

import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * JUnit tests for {@link IOManager}.
 * @author Giacomo Marciani {@literal <gmarciani@acm.org>}
 * @author Michele Porretta {@literal <mporretta@acm.org>}
 * @since 1.0
 * @see IOManager
 */
public class IOManagerTest {

  /**
   * Tests the {@link InputStream} creation from a local resource.
   * @throws IOException when {@link InputStream} cannot be opened.
   */
  @Test
  public void test_getInputStream_local() throws IOException {
    String resource = IOManagerTest.class.getResource("/tool/sample-read.txt").getPath();
    String actual;
    try (final InputStream in = IOManager.getInputStream(resource)) {
      actual = IOUtils.toString(in, Charset.defaultCharset());
    }
    final String expected = "Hello World!";
    Assert.assertEquals(expected, actual);
  }

  /**
   * Tests the {@link InputStream} creation from a remote resource.
   * @throws IOException when {@link InputStream} cannot be opened.
   */
  @Test
  public void test_getInputStream_remote() throws IOException {
    String resource = "http://www.google.com/robots.txt";
    String actual;
    try (final InputStream in = IOManager.getInputStream(resource)) {
      actual = IOUtils.toString(in, Charset.defaultCharset());
    }
    Assert.assertNotNull(actual);
  }

  /**
   * Tests the {@link OutputStream} creation from a local resource.
   * @throws IOException when {@link OutputStream} cannot be opened.
   */
  @SuppressWarnings("EmptyTryBlock")
  @Test
  public void test_getOutputStream_local() throws IOException {
    String resource = IOManagerTest.class.getResource("/tool/sample-write.txt").getPath();
    try (final OutputStream out = IOManager.getOutputStream(resource)) {
      //
    }
  }

  /**
   * Tests the {@link OutputStream} creation from a remote resource.
   */
  @SuppressWarnings("EmptyTryBlock")
  @Test
  public void test_getOutputStream_remote() {
    String resource = "http://www.google.com/robots.txt";
    try (final OutputStream out = IOManager.getOutputStream(resource)) {
      //
    } catch (IOException exc) { return; }
    Assert.fail();
  }

  @Test
  public void test_isWritableResource_local() {
    String resource = IOManagerTest.class.getResource("/tool/sample-write.txt").getPath();
    boolean result = IOManager.isWritableResource(resource);
    Assert.assertTrue(result);
  }

  @Test
  public void test_isWritableResource_remote() {
    String resource = "http://www.google.com/robots.txt";
    boolean result = IOManager.isWritableResource(resource);
    Assert.assertFalse(result);
  }

  /**
   * Tests read from a local resource.
   */
  @Test
  public void test_readResource_local() throws IOException {
    String resource = IOManagerTest.class.getResource("/tool/sample-read.txt").getPath();
    String actual = IOManager.readResource(resource);
    final String expected = "Hello World!";
    Assert.assertEquals(expected, actual);
  }

  /**
   * Tests read from a remote resource.
   */
  @Test
  public void test_readResource_remote() throws IOException {
    String resource = "http://www.google.com/robots.txt";
    String actual = IOManager.readResource(resource);
    Assert.assertNotNull(actual);
  }

  /**
   * Tests write to a local resource.
   */
  @Test
  public void test_writeResource_local() throws IOException {
    String resource = IOManagerTest.class.getResource("/tool/sample-write.txt").getPath();
    IOManager.writeResource(resource, "{}");
    String actual = IOManager.readResource(resource);
    final String expected = "{}";
    Assert.assertEquals(expected, actual);
  }

  /**
   * Tests write to a remote resource.
   * Should fail.
   */
  @Test
  public void test_writeResource_remote() {
    String resource = "http://www.google.com/robots.txt";
    try {
      IOManager.writeResource(resource, "{}");
    } catch (IOException exc) { return; }
    Assert.fail();
  }

  /**
   * Tests append to a local resource.
   * @throws IOException when errors in I/O.
   */
  @Test
  public void test_appendResource_local() throws IOException {
    String resource = IOManagerTest.class.getResource("/tool/sample-append.txt").getPath();
    Path path = FileSystems.getDefault().getPath(resource).toAbsolutePath();

    Files.write(path, "".getBytes());

    IOManager.appendResource(resource, "1");
    String actual1 = IOManager.readResource(resource);
    final String expected1 = "1";
    Assert.assertEquals(expected1, actual1);

    IOManager.appendResource(resource, "1");
    String actual2 = IOManager.readResource(resource);
    final String expected2 = "11";
    Assert.assertEquals(expected2, actual2);

    Files.write(path, "".getBytes());
  }

  /**
   * Tests append to a remote resource.
   * Should fail.
   */
  @Test
  public void test_appendResource_remote() {
    String resource = "http://www.google.com/robots.txt";
    try {
      IOManager.appendResource(resource, "1");
    } catch (IOException exc) { return; }
    Assert.fail();
  }
}
