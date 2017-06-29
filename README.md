# SOCSTREAM

*Soccer analytics leveraging Flink. Solution to DEBS 2013 Grand Challenge.*

*Coursework in Systems and Architectures for Big Data 2016/2017*


## Requirements
The system needs to be provided with the following packages:
* Java >= 1.8.0
* Maven >= 3.5.0
* Hadoop = 2.8.0
* Flink = 1.3.0 (scala 2.11)
* Kafka >= 0.10.2.1

and the following environment variables, pointing to the respective package home directory:
* JAVA_HOME
* MAVEN_HOME
* HADOOP_HOME
* FLINK_HOME
* KAFKA_HOME


## Build
Build the application for a specific query:

    $> mvn clean package

## Usage
Start the environment:

    $socstream_home> bash start-env.sh

Visit the Flink web dashboard at **http://localhost:8081**.

The general job submission is as follows:

    $flink_home> bin/flink jar <SOCSTREAM-JAR> [QUERY] [QUERY_OPTS]

where
* **[SOCSTREAM-JAR]** is the local absolute path to the Socstream's JAR;
* **[QUERY]** is the name of the Socstream query to execute;
* **[QUERY_OPTS]** are query arguments (e.g. --optName optValue).

Notice that the following map/reduce programs are available:
* **socstream-query-1** the 1st query, leveraging ... ;
* **socstream-query-2** the 2nd query, leveraging ... ;
* **socstream-query-3** the 3rd query, leveraging ... .

The job can be interrupted typing **Ctrl+C**.

Read the output:

    $hadoop_home> bin/hadoop hdfs -cat [RESULT]/*

where
*[RESULT]* is the HDFS directory of results.

Stop the environment:

    $socstream_home> bash stop-env.sh
    
    
## Kafka setup
First you need to create the Kafka topic `socstream`:

    $> sudo ${KAFKA_HOME}/bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic socstream

Test the topic creation:

    $> sudo ${KAFKA_HOME}/bin/kafka-topics.sh --list --zookeeper localhost:2181

To test message publishing:

    $> ${KAFKA_HOME}/bin/kafka-console-producer.sh --broker-list localhost:9092 --topic socstream

    $> ${KAFKA_HOME}/bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic socstream
    
    
## Query 1
The 1st query requires a netcat session to be started:

    $> ncat 127.0.0.1 9000 -l
    
The 1st query can be executed running:

    $socstream_home> bash socstream-query-1.sh
    
The output is saved to **${FLINK_HOME}/log/\*.out**.


## Query 2
The 2nd query requires a netcat session to be started:

    $> ncat 127.0.0.1 9000 -l
    
The 2nd query can be executed running:

    $socstream_home> bash socstream-query-2.sh
    
The output is saved to **${FLINK_HOME}/log/\*.out**.


## Query 3
The 3rd query requires a netcat session to be started:

    $> ncat 127.0.0.1 9000 -l
    
The 3rd query can be executed running:

    $socstream_home> bash socstream-query-3.sh
    
The output is saved to **${FLINK_HOME}/log/\*.out**.


## Dataset
The dataset is provided by DEBS Grand Challenge commitee and can be downloaded from [here](http://debs.org/?p=41).


## Authors
Giacomo Marciani, [gmarciani@acm.org](mailto:gmarciani@acm.org)

Michele Porretta, [mporretta@acm.org](mailto:mporretta@acm.org)


## References
Christopher Mutschler, Holger Ziekow, and Zbigniew Jerzak. 2013. The DEBS 2013 grand challenge. In Proceedings of the 7th ACM international conference on Distributed event-based systems (DEBS '13). ACM, New York, NY, USA, 289-294. [DOI](http://dx.doi.org/10.1145/2488222.2488283)


## License
The project is released under the [MIT License](https://opensource.org/licenses/MIT).
