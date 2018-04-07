package com.example.mongo.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.mongo.domain.Document;
import com.example.mongo.domain.Versioning;
import com.example.mongo.repository.DocumentRepository;
import com.example.mongo.service.DocumentSavingService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/documents")
public class DocumentEndpoint {

	private final DocumentRepository documentRepo;
	private final DocumentSavingService documentSavingService;

	public DocumentEndpoint(DocumentRepository documentRepo, DocumentSavingService documentSavingService) {
		this.documentRepo = documentRepo;
		this.documentSavingService = documentSavingService;
	}

	@GetMapping
	public Flux<Document> getDocuments() {
		return documentRepo.findAll();
	}

	@GetMapping("/{documentId}")
	public Mono<Document> getByDocumentId(@PathVariable String documentId) {
		return documentRepo.findById(documentId);
	}

	@GetMapping("/getAllByVersionSeriesId/{versionSeriesId}")
	public Flux<Document> getByVersionSeriesId(@PathVariable String versionSeriesId) {
		return documentRepo.findByVersionSeriesIdOrderByCreationDate(versionSeriesId);
	}

	@GetMapping("/getLastByVersionSeriesId/{versionSeriesId}")
	public Mono<Document> getLastByVersionSeriesId(@PathVariable String versionSeriesId) {
		return documentRepo.findByVersionSeriesIdOrderByCreationDate(versionSeriesId).last();
	}

	@PostMapping("/store")
	public Mono<Document> storeDocument(@RequestBody Document document) {
		return documentSavingService.saveDocument(document, Versioning.NEW);
	}

	@PostMapping("/update")
	public Mono<Document> updateDocument(@RequestBody Document document) {
		return documentSavingService.saveDocument(document, Versioning.UPDATE);
	}

	@PostMapping("/sameUpdate")
	public Mono<Document> updateSameDocument(@RequestBody Document document) {
		return documentSavingService.saveDocument(document, Versioning.SAME);
	}

	@GetMapping("/delete/{documentId}")
	public Mono<Void> deleteByDocumentId(@PathVariable String documentId) {
		return documentRepo.deleteById(documentId);
	}

	@GetMapping("/deleteAllByVersionSeriesId/{versionSeriesId}")
	public Mono<Void> deleteAllByVersionSeriesId(@PathVariable String versionSeriesId) {
		return documentRepo.deleteByVersionSeriesId(versionSeriesId);
	}
}
