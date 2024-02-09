package com.tcs.kafka.producers;

import com.tcs.kafka.constants.ApplicationConstants;
import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.IntegerSerializer;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.stream.IntStream;

/**
 * This class is used to send the data to topic without using transaction and with using callbacks
 */
public class MessageSenderWithoutTxn {

    public static void main(String[] args) {
        Callback callback = (metadata, exception) -> {
            if (exception != null) {
                System.out.println(" exception occurred while sending message " + exception);
            } else {
                System.out.println(" record sent successfully to partition --> " + metadata.partition() + " offset-->" + metadata.offset() + " topic -->" + metadata.topic());
            }
        };

        Properties p = new Properties();
        p.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        p.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, IntegerSerializer.class);
        p.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);

        KafkaProducer<Integer, String> kp = new KafkaProducer<>(p);
        IntStream.rangeClosed(1, 3).forEach(outer -> {
            IntStream.rangeClosed(1, 10).forEach(e -> {
                kp.send(new ProducerRecord<>(ApplicationConstants.topicName, e, "Message -->" + e), callback);
            });
            long ms=3000;
            System.out.println("sent batch  "+outer+"and will be slept for "+ms/1000+" seconds");
            try {
                Thread.sleep(ms);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        waitForFewSecondsToExecuteCallbacks();
    }

    private static void waitForFewSecondsToExecuteCallbacks() {
        try {
            System.out.println("All messages have been sent, just going to wait to execute callbacks");
            Thread.sleep(10000);
            System.out.println("wait time is over, main thread is going to die");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
