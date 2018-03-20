package com.example.products;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class ProductsApplications {

	public static void main(String[] args) {
		SpringApplication.run(ProductsApplications.class, args);
	}
}
