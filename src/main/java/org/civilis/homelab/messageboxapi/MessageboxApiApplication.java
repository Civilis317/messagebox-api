package org.civilis.homelab.messageboxapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@SpringBootApplication
public class MessageboxApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(MessageboxApiApplication.class, args);
	}

}
