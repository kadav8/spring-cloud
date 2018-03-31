package com.example.mongo.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.mongo.domain.Document;
import com.example.mongo.repository.DocumentRepository;
import com.example.mongo.service.DocumentSavingService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class DocumentEndpoint {

	@Autowired
	private DocumentRepository documentRepo;
	@Autowired
	private DocumentSavingService documentSavingService;

	@GetMapping("/documents")
	public Flux<Document> getDocuments() {
		return documentRepo.findAll();
	}

	@GetMapping("/document/getByDocumentId/{documentId}")
	public Mono<Document> getByDocumentId(@PathVariable String documentId) {
		return documentRepo.findById(documentId);
	}

	@GetMapping("/document/getByVersionSeriesId/{versionSeriesId}")
	public Flux<Document> getByVersionSeriesId(@PathVariable String versionSeriesId) {
		return documentRepo.findByVersionSeriesIdOrderByCreationDate(versionSeriesId);
	}

	@GetMapping("/document/getLastByVersionSeriesId/{versionSeriesId}")
	public Mono<Document> getLastByVersionSeriesId(@PathVariable String versionSeriesId) {
		return documentRepo.findByVersionSeriesIdOrderByCreationDate(versionSeriesId).last();
	}

	@PostMapping("/document/store")
	public Mono<Document> storeDocument(@RequestBody Document document) {
		return documentSavingService.storeDocument(document);
	}

	@PostMapping("/document/update")
	public Mono<Document> updateDocument(@RequestBody Document document) {
		return documentSavingService.updateDocument(document);
	}

	@PostMapping("/document/update/same")
	public Mono<Document> updateSameDocument(@RequestBody Document document) {
		return documentSavingService.updateSameDocument(document);
	}

	@GetMapping("/document/deleteByDocumentId/{documentId}")
	public Mono<Void> deleteByDocumentId(@PathVariable String documentId) {
		return documentRepo.deleteById(documentId);
	}

	@GetMapping("/document/deleteAllByVersionSeriesId/{versionSeriesId}")
	public Mono<Void> deleteAllByVersionSeriesId(@PathVariable String versionSeriesId) {
		return documentRepo.deleteByVersionSeriesId(versionSeriesId);
	}
}
