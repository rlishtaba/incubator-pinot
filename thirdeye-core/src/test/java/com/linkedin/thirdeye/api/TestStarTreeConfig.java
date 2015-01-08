package com.linkedin.thirdeye.api;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.linkedin.thirdeye.impl.StarTreeRecordStoreFactoryLogBufferImpl;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class TestStarTreeConfig
{
  @Test
  public void testBuild() throws Exception
  {
    // Builder
    StarTreeConfig.Builder builder = new StarTreeConfig.Builder();
    builder.setCollection("myCollection")
           .setDimensionNames(Arrays.asList("A", "B", "C"))
           .setMetricNames(Arrays.asList("M"))
           .setMetricTypes(Arrays.asList("INT"))           
           .setTimeColumnName("T")
           .setMaxRecordStoreEntries(1000)
           .setRecordStoreFactoryClass(StarTreeRecordStoreFactoryLogBufferImpl.class.getCanonicalName())
           .setRecordStoreFactoryConfig(new Properties());
    Assert.assertEquals(builder.getCollection(), "myCollection");
    Assert.assertEquals(builder.getDimensionNames(), Arrays.asList("A", "B", "C"));
    Assert.assertEquals(builder.getMetricNames(), Arrays.asList("M"));
    Assert.assertEquals(builder.getTimeColumnName(), "T");
    Assert.assertEquals(builder.getMaxRecordStoreEntries(), 1000);
    Assert.assertEquals(builder.getRecordStoreFactoryClass(), StarTreeRecordStoreFactoryLogBufferImpl.class.getCanonicalName());

    // Built config
    StarTreeConfig config = builder.build();
    Assert.assertEquals(config.getCollection(), "myCollection");
    Assert.assertEquals(config.getDimensionNames(), Arrays.asList("A", "B", "C"));
    Assert.assertEquals(config.getMetricNames(), Arrays.asList("M"));
    Assert.assertEquals(config.getTimeColumnName(), "T");
    Assert.assertEquals(config.getMaxRecordStoreEntries(), 1000);
    Assert.assertEquals(config.getRecordStoreFactoryClass(), StarTreeRecordStoreFactoryLogBufferImpl.class.getCanonicalName());
  }

  @Test
  public void testFromJson() throws Exception
  {
    JsonNode jsonNode = new ObjectMapper().readTree(ClassLoader.getSystemResourceAsStream("SampleConfig.json"));
    StarTreeConfig config = StarTreeConfig.fromJson(jsonNode);
    Assert.assertEquals(config.getCollection(), "myCollection");
    Assert.assertEquals(config.getDimensionNames(), Arrays.asList("A", "B", "C"));
    Assert.assertEquals(config.getMetricNames(), Arrays.asList("M"));
    Assert.assertEquals(config.getTimeColumnName(), "T");
    Assert.assertEquals(config.getRecordStoreFactoryClass(), StarTreeRecordStoreFactoryLogBufferImpl.class.getCanonicalName());
  }

  @Test
  public void testMissingRequired() throws Exception
  {
    String collection = "myCollection";
    List<String> dimensionNames = Arrays.asList("A", "B", "C");
    List<String> metricNames = Arrays.asList("M");

    StarTreeConfig.Builder builder = new StarTreeConfig.Builder();

    // Missing collection
    builder.setDimensionNames(dimensionNames).setMetricNames(metricNames);
    try { builder.build(); Assert.fail(); } catch (Exception e) { /* Good */ }
    builder.setDimensionNames(null).setMetricNames(null);

    // Missing dimension names
    builder.setCollection(collection).setMetricNames(metricNames);
    try { builder.build(); Assert.fail(); } catch (Exception e) { /* Good */ }
    builder.setCollection(null).setMetricNames(null);

    // Missing metric names
    builder.setCollection(collection).setDimensionNames(dimensionNames);
    try { builder.build(); Assert.fail(); } catch (Exception e) { /* Good */ }
    builder.setCollection(null).setDimensionNames(null);
  }
}
