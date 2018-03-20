package com.example.products.service;

import org.springframework.stereotype.Service;

import com.example.products.dto.ProductDto;

import reactor.core.publisher.Mono;

@Service
public class ProductService {

	public Mono<ProductDto> getProduct(String id) {
		ProductDto dummyProduct = new ProductDto(id, "Test Product " + id);
		return Mono.just(dummyProduct);
	}
}
