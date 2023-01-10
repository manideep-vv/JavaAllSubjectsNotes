package guru.learningjournal.kafka.examples;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.IntegerSerializer;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;
import java.util.UUID;

public class HelloProducerTest {
    public static void main(String[] args) {
        Properties p=new Properties();
        p.put(ProducerConfig.CLIENT_ID_CONFIG,"1dstr");
        p.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,AppConfigs.bootstrapServers);
        p.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, IntegerSerializer.class.getName());
        p.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        p.put(ProducerConfig.TRANSACTIONAL_ID_CONFIG,AppConfigs.transaction_id);

        KafkaProducer<Integer,String> kp=new KafkaProducer(p);
        kp.initTransactions();

        kp.beginTransaction();
        kp.send(new ProducerRecord<>(AppConfigs.topicName1,2,1,"5.1.2.p2- comitting this this is for  1 partition-"));
        kp.commitTransaction();
        System.out.println("this is  not committed to p1");
    }
}
