package guru.learningjournal.kafka.examples;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.IntegerSerializer;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

import static guru.learningjournal.kafka.examples.AppConfigs.topicName1;
import static guru.learningjournal.kafka.examples.AppConfigs.topicName2;

public class ProducerWithCommitAndAbort {
    public static void main(String[] args) {
        Properties p=new Properties();
        p.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, IntegerSerializer.class);
        p.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        p.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, AppConfigs.bootstrapServers_9092);
        p.put(ProducerConfig.TRANSACTIONAL_ID_CONFIG,"T1");

        KafkaProducer<Integer,String> kafkaProducer=new KafkaProducer<Integer, String>(p);
        kafkaProducer.initTransactions();// it is like registration
        kafkaProducer.beginTransaction();
        try{
            kafkaProducer.send(new ProducerRecord<>(topicName1,1,"Mesage-5")  );
            kafkaProducer.send(new ProducerRecord<>(topicName2,1,"Mesage-5")  );
            kafkaProducer.commitTransaction();
            System.out.println("txn comitted");
        }catch (Exception e){
            System.err.println("some exception occurred "+e);
            kafkaProducer.abortTransaction();
            System.err.println("txn aborted");
        }

        System.out.println("initiating 2nd transaction");
        kafkaProducer.initTransactions();
        kafkaProducer.beginTransaction();
        try{
            kafkaProducer.send(new ProducerRecord<>(topicName1,2,"Abort message -M1"));
            kafkaProducer.send(new ProducerRecord<>(topicName1,2,"Abort message -M2"));
            System.out.println(10/0);
        }catch (Exception e){
            System.err.println("some exception occurred "+e);
            kafkaProducer.abortTransaction();
            System.err.println("txn aborted");
        }
    }

}
