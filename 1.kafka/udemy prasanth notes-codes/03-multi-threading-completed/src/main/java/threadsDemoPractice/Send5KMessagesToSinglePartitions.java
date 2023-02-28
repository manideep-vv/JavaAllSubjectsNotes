package threadsDemoPractice;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.IntegerSerializer;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.IntStream;

public class Send5KMessagesToSinglePartitions {
    public static void main(String[] args) {
        Properties properties=new Properties();
        properties.put(ProducerConfig.CLIENT_ID_CONFIG,"1DSTR");
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,"localhost:9092,localhost:9093");
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, IntegerSerializer.class.getName());
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.put(ProducerConfig.TRANSACTIONAL_ID_CONFIG, UUID.randomUUID().toString());

        properties.put(ProducerConfig.ACKS_CONFIG,"all");
        properties.put(ProducerConfig.MAX_IN_FLIGHT_REQUESTS_PER_CONNECTION,5);
        properties.put(ProducerConfig.RETRIES_CONFIG,"2000");
        properties.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG,true);
        properties.put(ProducerConfig.DELIVERY_TIMEOUT_MS_CONFIG,"120000");//MAX RETRY FOR 120 SEC ELSE LEAVE IT , DONT RETRY ANY MORE
        properties.put(ProducerConfig.RETRY_BACKOFF_MS_CONFIG,1000);//WAIT FOR 1 SEC EVERYTIMEbefore u retry for the next time
        properties.put(ProducerConfig.LINGER_MS_CONFIG,1000);
        properties.put(ProducerConfig.COMPRESSION_TYPE_CONFIG,"snappy");
        properties.put(ProducerConfig.BATCH_SIZE_CONFIG,Integer.valueOf(32*1024));


        send100Messages(properties);

    }

    private static void send100Messages(Properties properties) {
        KafkaProducer<Integer,String> producer=new KafkaProducer<Integer, String>(properties);
        producer.initTransactions();
        producer.beginTransaction();
        System.out.println("started sending all messages"+properties);
        AtomicLong ms= new AtomicLong(0);
        Callback c=((metadata, exception) -> {
           if(exception!=null){
               System.out.println("exception came bro"+exception);
           }else{
               System.out.printf("\n got ack after %d ms and sent successfully to partition %s with offset %s",
                      System.currentTimeMillis()-ms.get(), metadata.partition(),metadata.offset());
           }
        });

            IntStream.range(0,5).forEach(e->{
                System.out.println("key--> "+e);
                ProducerRecord<Integer,String> rec=new ProducerRecord<>("infosys",e,"ori ne ayya"+e);
                producer.send(rec,c);
                ms.set(System.currentTimeMillis());
            });
            try {
                Thread.sleep(19000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        producer.commitTransaction();
        System.out.println("transaction committed");
}}
