#!/bin/bash

MOVIEDOOP_HOME="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"

##
# FLUME
##
cat /tmp/moviedoop.flume.movies_agent.pid | sudo kill -9
rm -rf /tmp/moviedoop.flume.movies_agent.pid

cat /tmp/moviedoop.flume.ratings_agent.pid | sudo kill -9
rm -rf /tmp/moviedoop.flume.ratings_agent.pid

cat /tmp/moviedoop.flume.query1_agent.pid | sudo kill -9
rm -rf /tmp/moviedoop.flume.query1_agent.pid

cat /tmp/moviedoop.flume.query2_agent.pid | sudo kill -9
rm -rf /tmp/moviedoop.flume.query2_agent.pid

cat /tmp/moviedoop.flume.query3_agent.pid | sudo kill -9
rm -rf /tmp/moviedoop.flume.query3_agent.pid

##
# HBASE
##
${HBASE_HOME}/bin/stop-hbase.sh

##
# HIVE
##
cat /tmp/moviedoop.hive.metastore.pid | sudo kill -9
rm -rf /tmp/moviedoop.hive.metastore.pid

##
# HADOOP
##
${HADOOP_HOME}/sbin/stop-dfs.sh
${HADOOP_HOME}/sbin/stop-yarn.sh
