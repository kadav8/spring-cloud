package com.example.admin.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.example.admin.dto.ProductDto;

import reactor.core.publisher.Mono;

@RestController
public class CatalogService {

	@Value("${example.products-app.name}")
	private String productsApp;
	@Value("${example.products-app.getProductUrl}")
	private String getProductUrl;

	@Autowired
	private WebClientTemplate client;

	@GetMapping("products/get/{id}")
	private Mono<ProductDto> getProduct(@PathVariable String id) {
		return client.get(productsApp, getProductUrl.replace("{id}", id), ProductDto.class);
	}
}
