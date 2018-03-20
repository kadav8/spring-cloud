package com.example.products.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.example.products.dto.ProductDto;
import com.example.products.service.ProductService;

import reactor.core.publisher.Mono;

@RestController
public class ProductsResource {

	@Autowired
	private ProductService productService;

	@GetMapping("products/get/{id}")
	public  Mono<ProductDto> getProduct(@PathVariable String id) {
		return productService.getProduct(id);
	}
}
