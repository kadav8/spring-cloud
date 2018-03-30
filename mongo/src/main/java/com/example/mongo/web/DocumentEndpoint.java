package com.example.mongo.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.mongo.domain.Document;
import com.example.mongo.repository.DocumentRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class DocumentEndpoint {

	@Autowired
	private DocumentRepository documentRepo;

	@GetMapping("/documents")
	public Flux<Document> getDocuments() {
		return documentRepo.findAll();
	}

	@GetMapping("/document/getByDocumentId/{documentId}")
	public Mono<Document> getByDocumentId(String documentId) {
		return documentRepo.findById(documentId);
	}

	@GetMapping("/document/getByVersionSeriesId/{versionSeriesId}")
	public Flux<Document> getByVersionSeriesId(String versionSeriesId) {
		return documentRepo.findByVersionSeriesIdOrderByCreationDate(versionSeriesId);
	}

	@GetMapping("/document/getLastByVersionSeriesId/{versionSeriesId}")
	public Mono<Document> getLastByVersionSeriesId(String versionSeriesId) {
		return documentRepo
				.findByVersionSeriesIdOrderByCreationDate(versionSeriesId)
				.last();
	}
}
