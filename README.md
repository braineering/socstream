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

If it is the first environment setup, you need to run:

    $socstream_home> bash start-env.sh format

WARNING: notice that the last command will format your HDFS.

The general job submission is as follows:

    $flink_home> bin/flink jar <SOCSTREAM-JAR> [QUERY] [HADOOP_OPTS] [PROGRAM_OPTS] <ARGS>

where
* **[SOCSTREAM-JAR]** is the local absolute path to the Socstream's JAR;
* **[QUERY]** is the name of the query to execute;
* **[HADOOP_OPTS]** are optional Hadoop options (e.g. -Dopt=val);
* **[PROGRAM_OPTS]** are optional program options (e.g. -D opt=val);
* **[ARGS]** are the mandatory program arguments.

Notice that the following map/reduce programs are available:
* **query-1** the 1st query, leveraging ... ;
* **query-2** the 2nd query, leveraging ... ;
* **query-3** the 3rd query, leveraging ... .

Read the output:

    $hadoop_home> bin/hadoop hdfs -cat [RESULT]/*

where
*[RESULT]* is the HDFS directory of results.

Stop the environment:

    $socstream_home> bash stop-env.sh
    
## Dataset
INSERT DESCRIPTION HERE


## Authors
Giacomo Marciani, [gmarciani@acm.org](mailto:gmarciani@acm.org)

Michele Porretta, [mporretta@acm.org](mailto:mporretta@acm.org)


## References
Christopher Mutschler, Holger Ziekow, and Zbigniew Jerzak. 2013. The DEBS 2013 grand challenge. In Proceedings of the 7th ACM international conference on Distributed event-based systems (DEBS '13). ACM, New York, NY, USA, 289-294. [DOI](http://dx.doi.org/10.1145/2488222.2488283)


## License
The project is released under the [MIT License](https://opensource.org/licenses/MIT).
