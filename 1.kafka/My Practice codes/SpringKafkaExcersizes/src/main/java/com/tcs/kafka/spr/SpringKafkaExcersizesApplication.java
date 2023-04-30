package com.tcs.kafka.spr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.core.query.Update;

@SpringBootApplication
public class SpringKafkaExcersizesApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringKafkaExcersizesApplication.class, args);
		Update u=new Update()
	}

}
