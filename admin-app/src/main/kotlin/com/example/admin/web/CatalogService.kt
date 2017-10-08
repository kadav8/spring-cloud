package com.example.admin.web

import com.example.admin.dto.ProductDto
import org.springframework.cloud.client.discovery.DiscoveryClient
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono
import java.util.*

@RestController
class CatalogService (private val wct: WebClientTemplate) {

    @GetMapping("products/get/{id}")
    fun getProduct(@PathVariable id: String): Mono<ProductDto> {
        return wct.get(
                "products-app",
                "products/get/" + id,
                ProductDto::class.java)
    }
}
