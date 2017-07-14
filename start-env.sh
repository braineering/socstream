#!/bin/bash

##
# ENVARS
##
export SOCSTREAM_HOME="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
export FLINK_CONF_DIR="${SOCSTREAM_HOME}/env/flink/conf"

##
# DIRECTORIES
##
OUTDIR="${SOCSTREAM_HOME}/out"
OUTDIR_QUERY1="${OUTDIR}/query-1"
OUTDIR_QUERY2="${OUTDIR}/query-2"
OUTDIR_QUERY3="${OUTDIR}/query-3"
mkdir -p ${OUTDIR_QUERY1}
mkdir -p ${OUTDIR_QUERY2}
mkdir -p ${OUTDIR_QUERY3}
chmod 777 ${OUTDIR_QUERY1}
chmod 777 ${OUTDIR_QUERY2}
chmod 777 ${OUTDIR_QUERY3}

sudo rm -f ${OUTDIR_QUERY1}/*
sudo rm -f ${OUTDIR_QUERY2}/*
sudo rm -f ${OUTDIR_QUERY3}/*

##
# FLINK
##
sudo rm -f ${FLINK_HOME}/log/*
${SOCSTREAM_HOME}/env/systemd/flink-manager.sh start

##
# KAFKA
##
${SOCSTREAM_HOME}/env/systemd/kafka-manager.sh start

##
# ELASTICSEARCH
##
${SOCSTREAM_HOME}/env/systemd/elasticsearch-manager.sh start

exit 0