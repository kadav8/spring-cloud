package com.example.mongo.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.mongo.domain.DocumentType;
import com.example.mongo.repository.DocumentTypeRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class DocumentTypeEndpoint {

	@Autowired
	private DocumentTypeRepository documentTypeRepo;

	@GetMapping("/types")
	public Flux<DocumentType> getTypes() {
		return documentTypeRepo.findAll();
	}

	@GetMapping("/type/getByName/{name}")
	public Mono<DocumentType> getByName(@PathVariable String name) {
		return documentTypeRepo.findById(name);
	}

	@PostMapping("/type/store")
	public Mono<DocumentType> storeDocument(@RequestBody DocumentType type) {
		return documentTypeRepo.save(type);
	}
}
