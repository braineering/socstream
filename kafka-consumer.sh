#!/bin/bash

##
# KAFKA COMMANDS
##
KAFKA_CONSUMER="${KAFKA_HOME}/bin/kafka-console-consumer.sh"

##
# KAFKA OPTIONS
##
BOOTSTRAP_SERVER="localhost:9092"
TOPIC_LIST="socstream"

##
# KAFKA CONSUMER
##
${KAFKA_CONSUMER} --bootstrap-server ${BOOTSTRAP_SERVER} --topic ${TOPIC_LIST}

