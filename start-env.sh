#!/bin/bash

##
# ENVARS
##
export SOCSTREAM_HOME="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"

##
# DIRECTORIES
##
mkdir -p "${SOCSTREAM_HOME}/output/query-1"
mkdir -p "${SOCSTREAM_HOME}/output/query-2"
mkdir -p "${SOCSTREAM_HOME}/output/query-3"

##
# FLINK
##
rm ${FLINK_HOME}/log/*
${SOCSTREAM_HOME}/env/systemd/flink-manager.sh start

##
# KAFKA
##
${SOCSTREAM_HOME}/env/systemd/kafka-manager.sh start