#!/bin/bash

##
# SETUP ENVARS
##
SOCSTREAM_HOME="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"

##
# FLINK COMMANDS
##
FLINK_RUN="${FLINK_HOME}/bin/flink run"

##
# SOCSTREAM
##
SOCSTREAM_JAR="${SOCSTREAM_HOME}/target/socstream-1.0-jar-with-dependencies.jar"
SOCSTREAM_QUERY="query-1"
SOCSTREAM_OPTS=""
SOCSTREAM_OPTS="${SOCSTREAM_OPTS} --kafka.zookeeper localhost:2181"
SOCSTREAM_OPTS="${SOCSTREAM_OPTS} --kafka.bootstrap localhost:9092"
SOCSTREAM_OPTS="${SOCSTREAM_OPTS} --kafka.topic socstream-topic-query-1"
SOCSTREAM_OPTS="${SOCSTREAM_OPTS} --parallelism 1"
SOCSTREAM_OPTS="${SOCSTREAM_OPTS} --data ${SOCSTREAM_HOME}/data/test/dataset.txt"
SOCSTREAM_OPTS="${SOCSTREAM_OPTS} --metadata ${SOCSTREAM_HOME}/data/test/metadata.yml"
SOCSTREAM_OPTS="${SOCSTREAM_OPTS} --parallelism 1"

##
# EXECUTION
##
${FLINK_RUN} ${SOCSTREAM_JAR} ${SOCSTREAM_QUERY} ${SOCSTREAM_OPTS}
