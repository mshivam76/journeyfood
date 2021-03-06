package org.brahmakumaris.journeyfood;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan("org.brahmakumaris.journeyfood")
@SpringBootApplication
public class JourneyfoodApplication {

	public static void main(String[] args) {
		SpringApplication.run(JourneyfoodApplication.class, args);
	}

}
