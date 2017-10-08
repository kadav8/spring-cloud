package com.example.admin

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.cloud.netflix.eureka.EnableEurekaClient

@SpringBootApplication
@EnableEurekaClient
class AdminApplication

fun main(args: Array<String>) {
    SpringApplication.run(AdminApplication::class.java, *args)
}
