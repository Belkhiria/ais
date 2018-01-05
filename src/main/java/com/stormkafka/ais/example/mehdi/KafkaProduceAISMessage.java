/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.stormkafka.ais.example.mehdi;

import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author Belkhiria
 */
public class KafkaProduceAISMessage {

    private static final String FILENAME = "C:\\Users\\Belkhiria\\Documents\\NetBeansProjects\\mavenproject1\\ais-example-mehdi\\src\\main\\java\\com\\stormkafka\\ais\\example\\mehdi\\ais_orbcomm_20170301_0000.nm4";

    public static void main(String[] args) {

        BufferedReader br = null;
        FileReader fr = null;

        // Build the configuration required for connecting to Kafka
        Properties props = new Properties();

        // List of kafka borkers. Complete list of brokers is not required as
        // the producer will auto discover the rest of the brokers.
        props.put("bootstrap.servers", "localhost:9092");
        props.put("batch.size", 1);
        // Serializer used for sending data to kafka. Since we are sending string,
        // we are using StringSerializer.
        props.put("key.serializer",
                "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer",
                "org.apache.kafka.common.serialization.StringSerializer");

        props.put("producer.type", "sync");

        // Create the producer instance
        Producer<String, String> producer = new KafkaProducer<>(props);

        try {

            fr = new FileReader(FILENAME);
            br = new BufferedReader(fr);

            String sCurrentLine;

            while ((sCurrentLine = br.readLine()) != null) {
                System.out.println("line : " + sCurrentLine);
                // Create message to be sent to "new_topic" topic with the line
                ProducerRecord<String, String> data = new ProducerRecord<>(
                        "new_topic", sCurrentLine, sCurrentLine);
                // Send the message
                producer.send(data);

            }

        } catch (IOException e) {

            e.printStackTrace();

        } finally {

            try {

                if (br != null) {
                    br.close();
                }

                if (fr != null) {
                    fr.close();
                }

            } catch (IOException ex) {

                ex.printStackTrace();

            }

        }

        // close the producer
        producer.close();
        System.out.println("end : ");
    }

}
