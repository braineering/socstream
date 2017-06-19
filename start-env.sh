#!/bin/bash

##
# ENVARS
##
export SOCSTREAM_HOME="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"

##
# FLINK
##
rm ${FLINK_HOME}/log/*
${FLINK_HOME}/bin/start-local.sh
