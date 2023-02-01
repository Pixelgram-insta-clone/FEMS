package com.cognizant.FEMS;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class FemsApplication {

	public static void main(String[] args) {
		SpringApplication.run(FemsApplication.class, args);
	}


}
