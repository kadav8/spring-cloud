package com.example.sql;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/documents")
public class DocumentEndpoints {

	private final DocumentRepository documentRepo;
	private final DocumentService documentSavingService;

	public DocumentEndpoints(DocumentRepository documentRepo, DocumentService documentSavingService) {
		this.documentRepo = documentRepo;
		this.documentSavingService = documentSavingService;
	}

	@GetMapping
	public List<Document> getDocuments() {
		return documentRepo.findAll();
	}

	@GetMapping("/{documentId}")
	public Document getByDocumentId(@PathVariable String documentId) {
		Optional<Document> doc = documentRepo.findById(documentId);
		return ( doc.isPresent() ) ? doc.get() : null;
	}

	@PostMapping("/save")
	public Document storeDocument(@RequestBody Document document) {
		return documentSavingService.saveDocument(document);
	}

	@PostMapping("/update")
	public Document updateDocument(@RequestBody Document document) {
		return documentSavingService.updateDocument(document);
	}

	@GetMapping("/delete/{documentId}")
	public void deleteByDocumentId(@PathVariable String documentId) {
		documentRepo.deleteById(documentId);
	}
}
