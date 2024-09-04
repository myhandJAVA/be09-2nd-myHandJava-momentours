package com.myhandjava.momentours;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MomentoursApplication {

	public static void main(String[] args) {
		SpringApplication.run(MomentoursApplication.class, args);
	}

}
