package com.example.mongo;

import static com.example.EnvironmentSetter.setEnvProperties;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class MongoApplication {

	public static void main(String[] args) {
		setEnvProperties();
		SpringApplication.run(MongoApplication.class, args);
	}
}
