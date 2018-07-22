package hu.example.redis;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/documents")
public class DocumentEndpoint {

	private final DocumentRepository documentRepo;
	private final DocumentService documentSavingService;

	public DocumentEndpoint(DocumentRepository documentRepo, DocumentService documentSavingService) {
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

	@PostMapping("/save")
	public Mono<Document> storeDocument(@RequestBody Document document) {
		return documentSavingService.saveDocument(document);
	}

	@PostMapping("/update")
	public Mono<Document> updateDocument(@RequestBody Document document) {
		return documentSavingService.updateDocument(document);
	}

	@GetMapping("/delete/{documentId}")
	public Mono<Void> deleteByDocumentId(@PathVariable String documentId) {
		return documentRepo.deleteById(documentId).flatMap(p -> Mono.<Void>empty());
	}
}
