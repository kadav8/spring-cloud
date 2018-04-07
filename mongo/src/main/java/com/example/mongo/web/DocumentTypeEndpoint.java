package com.example.mongo.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.mongo.domain.DocumentType;
import com.example.mongo.repository.DocumentTypeRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/types")
public class DocumentTypeEndpoint {

	private final DocumentTypeRepository documentTypeRepo;

	public DocumentTypeEndpoint(DocumentTypeRepository documentTypeRepo) {
		this.documentTypeRepo = documentTypeRepo;
	}

	@GetMapping
	public Flux<DocumentType> getTypes() {
		return documentTypeRepo.findAll();
	}

	@GetMapping("/{name}")
	public Mono<DocumentType> getByName(@PathVariable String name) {
		return documentTypeRepo.findById(name);
	}

	@PostMapping
	public Mono<DocumentType> storeDocumentType(@RequestBody DocumentType type) {
		return documentTypeRepo.save(type);
	}
}
