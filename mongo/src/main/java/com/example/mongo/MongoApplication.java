package com.example.mongo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import com.example.EnvSetter;

@SpringBootApplication
@EnableDiscoveryClient
public class MongoApplication {

	public static void main(String[] args) {
		EnvSetter.setEnvProperties();
		SpringApplication.run(MongoApplication.class, args);
	}
}
