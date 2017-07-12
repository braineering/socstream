#!/bin/bash

case "$1" in
start)  echo "[flink-manager] starting Apache Flink"
        ${FLINK_HOME}/bin/start-local.sh
        echo "[flink-manager] waiting Apache Flink to warm up (max: 60 seconds)"
        end="$(( SECONDS + 60 ))"
        while true; do
            [[ "200" = "$(curl --silent --write-out %{http_code} --output /dev/null http://localhost:8081)" ]] && break
            [[ "${SECONDS}" -ge "${end}" ]] && exit 1
            sleep 3
        done

        echo "[flink-manager] Apache Flink started"
        ;;
stop)   echo "[flink-manager] stopping Apache Flink..."
        sudo ${FLINK_HOME}/bin/stop-local.sh
        echo "[flink-manager] Apache Flink stopped"
        ;;
restart) echo "[flink-manager] restarting Apache Flink..."
        sudo systemctl stop flink.service
        sudo systemctl start flink.service
        echo "[flink-manager] Apache Flink restarted"
        ;;
reload|force-reload) echo "[flink-manager] Not yet implemented"
        ;;
*)      echo "Usage: flink-manager.sh {start|stop|restart|reload|force-reload}"
        exit 2
        ;;
esac
exit 0
