package com.tcs.kafka.producers;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.IntegerSerializer;
import org.apache.kafka.common.serialization.StringSerializer;

import java.time.Duration;
import java.time.Instant;
import java.util.Properties;
import java.util.UUID;
import java.util.stream.IntStream;

public class Send5KMessagesToDifferentPartitions {
    public static String topicName = "koni";
    public static void main(String[] args) {
        Properties properties=new Properties();
        properties.put(ProducerConfig.CLIENT_ID_CONFIG,"1DSTR");
//        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,"localhost:9092,localhost:9093");
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,"localhost:9092");
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
//        properties.put(ProducerConfig.TRANSACTIONAL_ID_CONFIG, UUID.randomUUID().toString());
        send100Messages(properties);

    }

    private static void send100Messages(Properties properties) {
        KafkaProducer<String,String> producer=new KafkaProducer<String, String>(properties);
//        producer.abortTransaction();
//        producer.initTransactions();
//        producer.beginTransaction();
        System.out.println("started sending all messages");

        Callback c=((metadata, exception) -> {
           if(exception!=null){
               System.out.println("exception came bro"+exception);
           }else{
               System.out.printf("\n got ack and sent successfully to topic %s partition %s with offset %s",metadata.topic(),metadata.partition(),metadata.offset());
           }
        });
        Instant now=Instant.now();
        IntStream.rangeClosed(1,5).forEach(ele->{
            IntStream.rangeClosed(1,200).forEach(e->{
                System.out.println("key--> "+e);
                String key=e%2==0?"even":"false";

                ProducerRecord<String,String> rec=new ProducerRecord<>(topicName,key,"ori ne ayya"+e);
                producer.send(rec,c);
            });
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        });
//        producer.commitTransaction();
        Duration d=Duration.between(now,Instant.now());
        System.out.println("completed the push in "+d.toMillis()+"millis");
}}
