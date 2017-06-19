#!/bin/bash

##
# SETUP ENVARS
##
export MOVIEDOOP_HOME="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"

##
# HADOOP
##
${HADOOP_HOME}/sbin/stop-dfs.sh
${HADOOP_HOME}/sbin/stop-yarn.sh

if [ "$1" = "format" ]; then
    sudo rm -rf /tmp/hadoop*
    ${HADOOP_HOME}/bin/hdfs namenode -format -force
fi

${HADOOP_HOME}/sbin/start-dfs.sh

${HADOOP_HOME}/bin/hdfs dfs -mkdir /user/

${HADOOP_HOME}/bin/hdfs dfs -mkdir /user/moviedoop
${HADOOP_HOME}/bin/hdfs dfs -mkdir /user/moviedoop/input
${HADOOP_HOME}/bin/hdfs dfs -mkdir /user/moviedoop/output
${HADOOP_HOME}/bin/hdfs dfs -mkdir /user/moviedoop/test
${HADOOP_HOME}/bin/hdfs dfs -mkdir /user/moviedoop/test/input
${HADOOP_HOME}/bin/hdfs dfs -mkdir /user/moviedoop/test/output

${HADOOP_HOME}/bin/hdfs dfs -mkdir /user/flume
${HADOOP_HOME}/bin/hdfs dfs -chmod g+w /user/flume

${HADOOP_HOME}/bin/hdfs dfs -mkdir /user/hive
${HADOOP_HOME}/bin/hdfs dfs -mkdir /user/hive/warehouse
${HADOOP_HOME}/bin/hdfs dfs -chmod g+w /user/hive/warehouse

${HADOOP_HOME}/bin/hdfs dfs -mkdir /tmp
${HADOOP_HOME}/bin/hdfs dfs -chmod g+w /tmp

##
# HIVE
##
if [ "$1" = "format" ]; then
    sudo -u postgres psql -f "${MOVIEDOOP_HOME}/lib/postgres/format-hive-metastore.sql"
    sudo -u postgres psql -f "${MOVIEDOOP_HOME}/lib/postgres/init-hive-metastore.sql"
    ${HIVE_HOME}/bin/schematool -initSchema -dbType postgres
    sudo -u postgres psql -f "${MOVIEDOOP_HOME}/lib/postgres/init-hive-metastore.sql"
fi
${HIVE_HOME}/bin/hive --service metastore > /dev/null 2>&1 &
echo $! > /tmp/moviedoop.hive.metastore.pid
sleep 30s
${HIVE_HOME}/bin/hive -f ${MOVIEDOOP_HOME}/lib/hive/movies.q
${HIVE_HOME}/bin/hive -f ${MOVIEDOOP_HOME}/lib/hive/ratings.q

##
# HBASE
##
${HBASE_HOME}/bin/stop-hbase.sh
${HBASE_HOME}/bin/start-hbase.sh
sleep 30s
if [ "$1" = "format" ]; then
    ${HBASE_HOME}/bin/hbase shell ${MOVIEDOOP_HOME}/lib/hbase/format-hbase.script
fi
${HBASE_HOME}/bin/hbase shell ${MOVIEDOOP_HOME}/lib/hbase/init-hbase.script

##
# FLUME
##
${FLUME_HOME}/bin/flume-ng agent -n movies_agent -f ${MOVIEDOOP_HOME}/lib/flume/conf/moviedoop-ingestion.conf -c ${MOVIEDOOP_HOME}/lib/flume/conf > /dev/null 2>&1 &
echo $! > /tmp/moviedoop.flume.movies_agent.pid

${FLUME_HOME}/bin/flume-ng agent -n ratings_agent -f ${MOVIEDOOP_HOME}/lib/flume/conf/moviedoop-ingestion.conf -c ${MOVIEDOOP_HOME}/lib/flume/conf > /dev/null 2>&1 &
echo $! > /tmp/moviedoop.flume.ratings_agent.pid

${FLUME_HOME}/bin/flume-ng agent -n query1_agent -f ${MOVIEDOOP_HOME}/lib/flume/conf/moviedoop-export.conf -c ${MOVIEDOOP_HOME}/lib/flume/conf > /dev/null 2>&1 &
echo $! > /tmp/moviedoop.flume.query1_agent.pid

${FLUME_HOME}/bin/flume-ng agent -n query2_agent -f ${MOVIEDOOP_HOME}/lib/flume/conf/moviedoop-export.conf -c ${MOVIEDOOP_HOME}/lib/flume/conf > /dev/null 2>&1 &
echo $! > /tmp/moviedoop.flume.query2_agent.pid

${FLUME_HOME}/bin/flume-ng agent -n query3_agent -f ${MOVIEDOOP_HOME}/lib/flume/conf/moviedoop-export.conf -c ${MOVIEDOOP_HOME}/lib/flume/conf > /dev/null 2>&1 &
echo $! > /tmp/moviedoop.flume.query3_agent.pid
