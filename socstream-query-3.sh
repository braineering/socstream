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
# SETUP
##
mkdir -p "${SOCSTREAM_HOME}/output/query-3"

##
# SOCSTREAM
##
SOCSTREAM_JAR="${SOCSTREAM_HOME}/target/socstream-1.0-jar-with-dependencies.jar"
SOCSTREAM_QUERY="query-3"
SOCSTREAM_OPTS=""
SOCSTREAM_OPTS="${SOCSTREAM_OPTS} --kafka.zookeeper localhost:2181"
SOCSTREAM_OPTS="${SOCSTREAM_OPTS} --kafka.bootstrap localhost:9092"
SOCSTREAM_OPTS="${SOCSTREAM_OPTS} --kafka.topic socstream"
SOCSTREAM_OPTS="${SOCSTREAM_OPTS} --windowSize 0"
SOCSTREAM_OPTS="${SOCSTREAM_OPTS} --windowUnit SECONDS"
SOCSTREAM_OPTS="${SOCSTREAM_OPTS} --parallelism 1"
SOCSTREAM_OPTS="${SOCSTREAM_OPTS} --metadata ${SOCSTREAM_HOME}/data/test/metadata.yml"
SOCSTREAM_OPTS="${SOCSTREAM_OPTS} --output ${SOCSTREAM_HOME}/output/query-3/socstream-query-3.out"

##
# EXECUTION
##
${FLINK_RUN} ${SOCSTREAM_JAR} ${SOCSTREAM_QUERY} ${SOCSTREAM_OPTS}
