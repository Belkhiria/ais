# ais

zkServer  
zkClient  

kafka-server-start.bat .\config\server.properties  
kafka-topics.bat --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic new_topic  
kafka-console-producer.bat --broker-list localhost:9092 --topic new_topic  
kafka-console-consumer.bat --zookeeper localhost:2181 --topic new_topic  
