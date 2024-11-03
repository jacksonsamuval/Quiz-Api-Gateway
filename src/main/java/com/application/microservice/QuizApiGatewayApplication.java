package com.application.microservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.reactive.config.CorsRegistry;

@SpringBootApplication
@EnableDiscoveryClient
public class QuizApiGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(QuizApiGatewayApplication.class, args);
	}
}
