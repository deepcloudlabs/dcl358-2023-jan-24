package com.example.crm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class CrmReactiveClientMicroserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CrmReactiveClientMicroserviceApplication.class, args);
	}

}
