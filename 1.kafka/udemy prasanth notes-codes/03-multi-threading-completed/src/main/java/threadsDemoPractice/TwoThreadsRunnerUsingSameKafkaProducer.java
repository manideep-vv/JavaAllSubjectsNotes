package threadsDemoPractice;

import guru.learningjournal.kafka.examples.AppConfigs;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.IntegerSerializer;
import org.apache.kafka.common.serialization.StringSerializer;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;
import java.util.stream.IntStream;

public class TwoThreadsRunnerUsingSameKafkaProducer {
    public static void main(String[] args) throws Exception {

        InputStream is=new FileInputStream("kafka.properties");
        Properties p=new Properties();
        p.load(is);
        p.put(ProducerConfig.CLIENT_ID_CONFIG,"1dstr");
        p.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, IntegerSerializer.class.getName());
        p.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        KafkaProducer<Integer,String> producer=new KafkaProducer<Integer, String>(p);
        Thread[] arr=new Thread[2];
        IntStream.rangeClosed(0,1).forEach(num->{
            MyFileRunnable myFileRunnable=new MyFileRunnable(producer,"nse-eod-topic", AppConfigs.eventFiles[num]);
            arr[num] =new Thread(myFileRunnable);
            arr[num].start();
            try {
                arr[num].join();
                Thread.sleep(3000);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            System.err.println("\n  got acks for --> "+myFileRunnable.counter );
        });


        arr[0].join();
        arr[1].join();

        System.out.println("all threads completed the tasks succesfully and acks received for ");
        System.out.println("properties size"+p.size());



    }
}
