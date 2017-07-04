#!/bin/bash

##
# ENVARS
##
export SOCSTREAM_HOME="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"

##
# DIRECTORIES
##
OUTDIR="${SOCSTREAM_HOME}/output"
OUTDIR_QUERY1="${OUTDIR}/query-1"
OUTDIR_QUERY2="${OUTDIR}/query-2"
OUTDIR_QUERY3="${OUTDIR}/query-3"
mkdir -p ${OUTDIR_QUERY1}
mkdir -p ${OUTDIR_QUERY2}
mkdir -p ${OUTDIR_QUERY3}
chmod 777 ${OUTDIR_QUERY1}
chmod 777 ${OUTDIR_QUERY2}
chmod 777 ${OUTDIR_QUERY3}

##
# FLINK
##
rm ${FLINK_HOME}/log/*
${SOCSTREAM_HOME}/env/systemd/flink-manager.sh start

##
# KAFKA
##
${SOCSTREAM_HOME}/env/systemd/kafka-manager.sh start