package com.myhandjava.momentoursserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class MomentoursServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(MomentoursServerApplication.class, args);
	}

}
