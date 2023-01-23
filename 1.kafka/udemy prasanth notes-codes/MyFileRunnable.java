package threadsDemoPractice;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * The main intention of this class is it says no need to have multiple kafka producers in
 * same application, create multiple threads all threads can use same kafka producer
 * let each thread read data from some file
 */
public class MyFileRunnable implements Runnable {

    KafkaProducer<Integer,String> producer;
    String topic;
    String fileLocation;


int counter =0;

    public MyFileRunnable(KafkaProducer<Integer,String> producer, String topic, String fileLocation){
    this.producer=producer;
    this.topic=topic;
    this.fileLocation=fileLocation;
    }

    @Override
    public void run() {
        Path path = Paths.get(fileLocation);
        List<String> allLines = null;
        Callback c=getCallback();

        try {
            allLines = Files.readAllLines(path);
            System.out.printf("started sending all %d records ",allLines.size());
            for (String line: allLines) {
                ProducerRecord<Integer,String>  record=
                        new ProducerRecord<>(topic,null,line);
                producer.send(record,c);
            }
            System.out.printf("all %d messages are sent by thread name %s",allLines.stream().count(),
                    Thread.currentThread().getName());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("size of the list is--> "+allLines.size());



    }
    public Callback getCallback(){
        Callback c= (metadata,exception)->{
            if(exception==null){
                ++counter;
                System.out.printf("\n msg was written to partition %d , with offset %s by Thread %s",metadata.partition(),
                        metadata.offset(),Thread.currentThread().getName());
            }else{
                System.out.println("exception occured and failed to send message due to --> "+exception);
            }

        };
        return c;
    }
}
