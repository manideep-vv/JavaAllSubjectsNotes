package threadsDemoPractice;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicInteger;

public class MykafkaConsumer {

    public static void main(String[] args) {
        Properties p=new Properties();
        p.put(ConsumerConfig.GROUP_ID_CONFIG,"1.infosys-group");
        p.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,"localhost:9092,localhost:9093");
        p.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG,true);
        p.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG,"latest");
        p.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        p.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());

        KafkaConsumer<String,String> consumer=new KafkaConsumer<String, String>(p);
        consumer.subscribe(Arrays.asList("infosys"));
        List<ConsumerRecord<String,String>> records=new ArrayList<>();
        AtomicInteger count=new AtomicInteger(0);
        System.out.println("started listening");
        while(true){
            ConsumerRecords<String, String> rec = consumer.poll(Duration.ofMillis(100));
            rec.forEach(singleRecord->{
                records.add(singleRecord);
                System.out.printf("\n %d received a msg--> %s from partition %d offset %d ",count.incrementAndGet(),singleRecord.value(),singleRecord.partition(),singleRecord.offset());
            });
        }





    }
}
