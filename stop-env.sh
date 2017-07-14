#!/bin/bash

##
# ENVARS
##
export SOCSTREAM_HOME="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"

##
# FLINK
##
${SOCSTREAM_HOME}/env/systemd/flink-manager.sh stop

##
# KAFKA
##
${SOCSTREAM_HOME}/env/systemd/kafka-manager.sh stop

##
# ELASTICSEARCH
##
${SOCSTREAM_HOME}/env/systemd/elasticsearch-manager.sh stop

##
# ENVARS
##
unset SOCSTREAM_HOME
unset FLINK_CONF_DIR

exit 0
