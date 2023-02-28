package threadsDemoPractice;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.IntegerSerializer;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;
import java.util.UUID;
import java.util.stream.IntStream;

public class Send5KMessagesToDifferentPartitions {
    public static void main(String[] args) {
        Properties properties=new Properties();
        properties.put(ProducerConfig.CLIENT_ID_CONFIG,"1DSTR");
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,"localhost:9092,localhost:9093");
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, IntegerSerializer.class.getName());
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.put(ProducerConfig.TRANSACTIONAL_ID_CONFIG, UUID.randomUUID().toString());
        send100Messages(properties);

    }

    private static void send100Messages(Properties properties) {
        KafkaProducer<Integer,String> producer=new KafkaProducer<Integer, String>(properties);
        producer.initTransactions();
        producer.beginTransaction();
        System.out.println("started sending all messages");

        Callback c=((metadata, exception) -> {
           if(exception!=null){
               System.out.println("exception came bro"+exception);
           }else{
               System.out.printf("\n got ack and sent successfully to partition %s with offset %s",metadata.partition(),metadata.offset());
           }
        });
        IntStream.rangeClosed(0,10).forEach(ele->{
            IntStream.range(0,10).forEach(e->{
                System.out.println("key--> "+e);
                ProducerRecord<Integer,String> rec=new ProducerRecord<>("tcs",e,"ori ne ayya"+e);
                producer.send(rec,c);
            });
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        });
        producer.commitTransaction();
        System.out.println("transaction committed");
}}
