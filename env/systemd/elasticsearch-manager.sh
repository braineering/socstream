#!/bin/bash

case "$1" in
start)  echo "[elasticsearch-manager] starting Elasticsearch"
        ${ELASTICSEARCH_HOME}/bin/elasticsearch -d -p ${ELASTICSEARCH_HOME}/elasticsearch.pid
        echo "[elasticsearch-manager] waiting Elasticsearch to warm up (max: 60 seconds)"
        end="$(( SECONDS + 60 ))"
        while true; do
            [[ "200" = "$(curl --silent --write-out %{http_code} --output /dev/null http://localhost:9200)" ]] && break
            [[ "${SECONDS}" -ge "${end}" ]] && exit 1
            sleep 3
        done

        echo "[elasticsearch-manager] Elasticsearch started"
        ;;
stop)   echo "[elasticsearch-manager] stopping Elasticsearch..."
        cat ${ELASTICSEARCH_HOME}/elasticsearch.pid | kill
        echo "[elasticsearch-manager] Elasticsearch stopped"
        ;;
restart) echo "[elasticsearch-manager] restarting Elasticsearch..."
        sudo systemctl stop elasticsearch.service
        sudo systemctl start elasticsearch.service
        echo "[elasticsearch-manager] Elasticsearch restarted"
        ;;
reload|force-reload) echo "[elasticsearch-manager] Not yet implemented"
        ;;
*)      echo "Usage: elasticsearch-manager.sh {start|stop|restart|reload|force-reload}"
        exit 2
        ;;
esac
exit 0
