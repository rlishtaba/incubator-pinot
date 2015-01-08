package com.linkedin.thirdeye.api;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface StarTree
{
  StarTreeConfig getConfig();

  /**
   * Given a getAggregate consisting of specific dimension values, searches the tree for the corresponding
   * aggregate metrics.
   *
   * @param query
   *  A query with fixed and/or "*" values for each dimension
   * @return
   *  The representative record containing aggregate metrics.
   */
  StarTreeRecord getAggregate(StarTreeQuery query);

  /**
   * @param query
   *  A query with fixed and/or "*" values for each dimension
   * @return
   *  The time series values corresponding to query
   */
  List<StarTreeRecord> getTimeSeries(StarTreeQuery query);

  /**
   * Adds a record to the tree.
   */
  void add(StarTreeRecord record);

  /**
   * Opens all resources at leaves
   */
  void open() throws IOException;

  /**
   * Closes all resources at leaves
   */
  void close() throws IOException;

  /**
   * @return
   *  A set of every observed value for a dimension in the tree
   */
  Set<String> getDimensionValues(String dimensionName, Map<String, String> fixedDimensions);

  /**
   * @return
   *  The root node in the tree
   */
  StarTreeNode getRoot();

  StarTreeRecordStoreFactory getRecordStoreFactory();

  /**
   * @return
   *  The most specific leaf node in the tree to which getAggregate maps
   */
  StarTreeNode find(StarTreeQuery query);

  /**
   * @return
   *  All leaf nodes (i.e. star nodes as well) to which the getAggregate maps
   */
  Collection<StarTreeNode> findAll(StarTreeQuery query);

  /**
   * @return
   *  Statistics about the tree
   */
  StarTreeStats getStats();
}
