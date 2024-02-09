package com.tcs.kafka.streams;

import com.tcs.kafka.constants.ApplicationConstants;
import com.tcs.kafka.producers.MessageSenderWithoutTxn;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.KStream;

import java.util.Properties;
//read docsn java classes
//practice with sending json objects
//practice multi threaded with more threads
public class BasicStreamsConsumerAndPrinter {

    public static void main(String[] args) {
        Properties p=new Properties();
        p.put(StreamsConfig.APPLICATION_ID_CONFIG,"manideep");
        p.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG,"localhost:9092");
        p.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.Integer().getClass());
        p.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG,Serdes.String().getClass());
        p.put(StreamsConfig.NUM_STREAM_THREADS_CONFIG,3);

        StreamsBuilder sb=new StreamsBuilder();//This is to create a stream on a topic
        KStream<Integer, String> stream = sb.stream(ApplicationConstants.topicName);
        System.out.println("stream is created on a topic "+ApplicationConstants.topicName);

        stream.foreach((k,v)->{
            System.out.printf("\n "+Thread.currentThread().getName()+" received kafka message as key--> %s, value --> %s",k,v);
        });

        Topology topology=sb.build();
        KafkaStreams ks=new KafkaStreams(topology,p);
        ks.start();
        System.out.println("stream started");

        Runtime.getRuntime().addShutdownHook(new Thread(()->{
            System.out.println("executing shutdown hook code to close the streams");
            ks.close();
        }));

    }
}
