#!/bin/bash

case "$1" in
start)  echo "[kafka-manager] starting kafka..."
        sudo ${KAFKA_HOME}/bin/zookeeper-server-start.sh ${KAFKA_HOME}/config/zookeeper.properties > /dev/null &
	    sleep 5
        sudo ${KAFKA_HOME}/bin/kafka-server-start.sh -daemon ${KAFKA_HOME}/config/server.properties
        echo "[kafka-manager] kafka started"
        ;;
stop)   echo "[kafka-manager] stopping kafka..."
        sudo ${KAFKA_HOME}/bin/kafka-server-stop.sh
	    sudo ${KAFKA_HOME}/bin/zookeeper-server-stop.sh
	    sudo ${KAFKA_HOME}/bin/zookeeper-server-stop.sh
        echo "[kafka-manager] kafka stopped"
        ;;
restart) echo "[kafka-manager] restarting kafka..."
        sudo systemctl stop kafka
        sudo systemctl start kafka
        echo "[kafka-manager] kafka restarted"
        ;;
reload|force-reload) echo "[kafka-manager] Not yet implemented"
        ;;
*)      echo "Usage: kafka.sh {start|stop|restart}"
        exit 2
        ;;
esac
exit 0

