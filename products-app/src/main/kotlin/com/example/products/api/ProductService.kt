package com.example.products.api

import com.example.products.dto.ProductDto
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController
class ProductService {

    @GetMapping("products/get/{id}")
    fun getProduct(@PathVariable id: String): Mono<ProductDto> {
        val dummyProduct = ProductDto(
                id = id,
                name = "Test Product")
        return Mono.just(dummyProduct)
    }
}