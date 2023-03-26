package com.tcs.kafka.consumers;

import com.tcs.kafka.producers.Send5KMessagesToDifferentPartitions;
import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.serialization.IntegerDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.util.Collections;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicInteger;

public class MyConsumerNotAutoCommit {
    public static Properties getAllConsumerProperties(){
        Properties p=new Properties();
        p.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,"localhost:9092");
//        p.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,"localhost:9093,localhost:9095");
        p.put(ConsumerConfig.GROUP_ID_CONFIG,"GROUP-earliest-g3");
        p.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG,false);
        p.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG,5*1000);
        p.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, OffsetResetStrategy.EARLIEST.toString().toLowerCase());
        p.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        p.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        return p;
    }
    public static void main(String[] args) {
        AtomicInteger  count=new AtomicInteger(0);
        KafkaConsumer<Integer,String> cons=new KafkaConsumer<Integer, String>(getAllConsumerProperties());
        cons.subscribe(Collections.singletonList(Send5KMessagesToDifferentPartitions.topicName));
        while(true) {
            System.out.println("Bro polling started bro");
//            System.out.println("Bro polling started bro");
            ConsumerRecords<Integer, String> records = cons.poll(1000);
            System.out.println("polling completed");
            for (ConsumerRecord<Integer, String> rec : records) {
                System.err.printf("\n %d. rec rxd from partition %d offset %d key --> %s value -->%s", count.incrementAndGet(), rec.partition(), rec.offset(), rec.key(),rec.value());
            }
        }
    }
}

