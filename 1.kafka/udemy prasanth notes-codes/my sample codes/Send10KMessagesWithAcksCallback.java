package threadsDemoPractice;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

/**
 * this class will send 10 thousand messages to the cluster brokers in 3 modes
 * 1) With partition number --> first it will send with each and every message with partition number and
 *      see if all messages are going to same partition or not with callback
 * 2) Without partition number same key-->  this time it will send without partition number and only with same key
 *  and ensure all messages having same key should go to same partition and diff key to diff partition num
 *  3)  Without key- now since no partition num and no key all should be sent to all partitions in
 *  round robin fashion
 *
 *  command used for topic creation
 *  C:\kafka_2.13-3.3.1\bin\windows\kafka-topics.bat --create --bootstrap-server localhost:9092 --topic EmployeesInfo
 *  --partitions 5 --replication-factor 3 --config min.insync.replicas=2
 */

public class Send10KMessagesWithAcksCallback {
    public static void main(String[] args) {
        Properties p=new Properties();
        p.put(ProducerConfig.CLIENT_ID_CONFIG,"1dstr");
        //every producer instance in a cluster should have unique transaction id
        p.put(ProducerConfig.TRANSACTIONAL_ID_CONFIG, UUID.randomUUID().toString());
        p.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,"localhost:9092,localhost:9093,localhost:9094");
        //we should serialize both key and value while sending
        p.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        p.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,StringSerializer.class.getName());
        sendAllMessagesToSamePartition(p);
    }
    public static void sendAllMessagesToSamePartition(Properties p){
        KafkaProducer<String,String> producer=new KafkaProducer<String, String>(p);
        producer.initTransactions();
        producer.beginTransaction();
        System.out.println("txn began started writing all messages");
        AtomicInteger counter=new AtomicInteger(0);
        Callback callback=(recMetadata,exception)->{
            if (exception == null) {
            int callbackExecutedCount= counter.getAndIncrement();
            System.out.printf("\n %d. msg went to topic --> %s partition --> %d , offset --> %s ",
                    callbackExecutedCount,recMetadata.topic(),recMetadata.partition(),recMetadata.offset());
            }
        };
        IntStream.range(1,6000).forEach(num->{
        //Here we are sending all messages to partition number 1, so all msgs will be sent to p1
            // and key is unique for every message
            // and as we are already giving partition num,so it will not decide the partition based on the key
            ProducerRecord<String,String> record=
                new ProducerRecord<>("EmployeesInfo","orey naina--"+num);
            // this callback method will be executed each and every time for each and every message
            producer.send(record,callback);
        });
//even though u didnt committed all messages will be written to broker,
// once u committed it will change the flag to committed for each and every message
        producer.commitTransaction();
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("sent all 6000 messages and committed too and got acks for "+counter.get());
    }
}
