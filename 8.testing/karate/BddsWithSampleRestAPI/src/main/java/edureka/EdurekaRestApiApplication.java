package edureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class EdurekaRestApiApplication {
	@PostConstruct
	public static void main(String[] args) {
		SpringApplication.run(EdurekaRestApiApplication.class, args);
	}

}
