package com.example.products

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.cloud.netflix.eureka.EnableEurekaClient

@SpringBootApplication
@EnableEurekaClient
class ProductsApplication

fun main(args: Array<String>) {
    SpringApplication.run(ProductsApplication::class.java, *args)
}
