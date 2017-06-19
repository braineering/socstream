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

import com.acmutv.socstream.common.db.DbConfiguration;
import com.acmutv.socstream.common.source.KafkaProperties;
import com.acmutv.socstream.common.source.SourceType;
import org.junit.Assert;
import org.junit.Test;

import java.io.*;
import java.util.ArrayList;

/**
 * JUnit tests for {@link AppConfigurationService} and {@link AppConfiguration}.
 * @author Giacomo Marciani {@literal <gmarciani@acm.org>}
 * @author Michele Porretta {@literal <mporretta@acm.org>}
 * @since 1.0
 * @see AppConfigurationService
 */
public class AppConfigurationServiceTest {

  /**
   * Tests the restoring of default settings in app configuration.
   */
  @Test
  public void test_fromDefault() {
    AppConfiguration actual = AppConfigurationService.fromDefault();
    AppConfiguration expected = new AppConfiguration();
    Assert.assertEquals(expected, actual);
  }

  /**
   * Tests the app configuration parsing from an external YAML file.
   * In this test the configuration file provides with complete custom settings.
   * The configuration file has non-null values and template string (${RES}).
   */
  @Test
  public void test_fromJsonYaml_custom_file() throws IOException {
    InputStream in = AppConfigurationServiceTest.class.getResourceAsStream("/config/custom1.yaml");
    AppConfiguration actual = AppConfigurationService.fromYaml(in);
    AppConfiguration expected = new AppConfiguration();
    expected.setSource(SourceType.FILE);
    expected.setDataset("CustomDataset");
    expected.setParallelism(16);
    Assert.assertEquals(expected, actual);
  }

  /**
   * Tests the app configuration parsing from an external YAML file.
   * In this test the configuration file provides with complete custom settings.
   * The configuration file has non-null values and template string (${RES}).
   */
  @Test
  public void test_fromJsonYaml_custom_kafka() throws IOException {
    InputStream in = AppConfigurationServiceTest.class.getResourceAsStream("/config/custom2.yaml");
    AppConfiguration actual = AppConfigurationService.fromYaml(in);
    AppConfiguration expected = new AppConfiguration();
    expected.setSource(SourceType.KAFKA);
    expected.setTopic("CustomTopic");
    expected.setKafkaProperties(new KafkaProperties(
        "CustomBootstrap", "CustomZookeper", "CustomGroup"
    ));
    expected.setParallelism(16);
    Assert.assertEquals(expected, actual);
  }

  /**
   * Tests the app configuration parsing from an external YAML file.
   * In this test the configuration file provides with complete default settings.
   */
  @Test
  public void test_fromJsonYaml_default() throws IOException {
    InputStream in = AppConfigurationServiceTest.class.getResourceAsStream("/config/default.yaml");
    AppConfiguration actual = AppConfigurationService.fromYaml(in);
    AppConfiguration expected = new AppConfiguration();
    Assert.assertEquals(expected, actual);
  }

  /**
   * Tests the configuration parsing from an external YAML file.
   * In this test the configuration file provides with empty settings.
   */
  @Test
  public void test_fromJsonYaml_empty() throws IOException {
    InputStream in = AppConfigurationServiceTest.class.getResourceAsStream("/config/empty.yaml");
    try {
      AppConfigurationService.fromYaml(in);
    } catch (IOException exc) {return;}
    Assert.fail();
  }

  /**
   * Tests the configuration parsing from an external YAML configuration file.
   * In this test the configuration file provides with partial custom settings.
   */
  @Test
  public void test_fromJsonYaml_partialCustom() throws IOException {
    InputStream in = AppConfigurationServiceTest.class.getResourceAsStream("/config/partial.yaml");
    AppConfiguration actual = AppConfigurationService.fromYaml(in);
    AppConfiguration expected = new AppConfiguration();
    Assert.assertEquals(expected, actual);
  }

}