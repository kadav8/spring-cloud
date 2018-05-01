package com.example.sql;

import static com.example.EnvironmentSetter.setEnvProperties;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SqlApplication {

	public static void main(String[] args) {
		setEnvProperties();
		SpringApplication.run(SqlApplication.class, args);
	}
}
