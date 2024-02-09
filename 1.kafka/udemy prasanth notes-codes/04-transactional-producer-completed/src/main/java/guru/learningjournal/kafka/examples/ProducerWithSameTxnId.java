package guru.learningjournal.kafka.examples;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.IntegerSerializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Properties;

public class ProducerWithSameTxnId {
    private static final Logger logger = LogManager.getLogger();

    public static void main(String[] args) throws Exception {

        Properties props = new Properties();
//        props.put(ProducerConfig.CLIENT_ID_CONFIG, AppConfigs.applicationID);
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, AppConfigs.bootstrapServers_9092);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, IntegerSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.put(ProducerConfig.TRANSACTIONAL_ID_CONFIG, AppConfigs.transaction_id);

        logger.info("all properties are initialised and Creating Kafka Producer...");


        KafkaProducer<Integer, String> producer = new KafkaProducer<>(props);
        System.out.println("Created producer");
        producer.initTransactions();

        long l=1000;
        System.out.println("current thread will be slept for "+l/1000+"seconds");
        Thread.sleep(l);
        System.out.println("current thread is wakenup from slept ");

        logger.info("Starting First Transaction...");
        producer.beginTransaction();
        try {
            for (int i = 1; i <= AppConfigs.numEvents; i++) {
                producer.send(new ProducerRecord<>(AppConfigs.topicName1, i, "Simple Message-T1-" + i));
                producer.send(new ProducerRecord<>(AppConfigs.topicName2, i, "Simple Message-T1-" + i));
            }
            logger.info("Committing First Transaction.");
            producer.commitTransaction();
        }catch (Exception e){
            logger.error("Exception in First Transaction. Aborting...",e);
            producer.abortTransaction();
            producer.close();
            throw new RuntimeException(e);
        }

    }
}
