package com.example.mongo;

import java.util.Date;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import reactor.core.publisher.Mono;

@Service
public class DocumentService {
	private Logger LOG = LoggerFactory.getLogger(DocumentService.class);

	private final DocumentRepository documentRepository;
	private final NotificationSender notificationSender;

	public DocumentService(DocumentRepository documentRepository, NotificationSender notificationSender) {
		this.documentRepository = documentRepository;
		this.notificationSender = notificationSender;
	}

	public Mono<Document> saveDocument(final Document document) {
		LOG.debug("Save: {}", document);
		return documentRepository
				.save(getSaveableDocument(document))
				.onErrorMap(e -> {
					if(e.toString().contains("DuplicateKeyException")) {
						throw new RuntimeException("DocumentId already exists: " + document.getDocumentId());
					}
					return e;
				})
				.doOnSuccess(savedDocument -> notificationSender.sendSaveSuccess(savedDocument.getDocumentId()));
	}

	public Mono<Document> updateDocument(final Document document) {
		LOG.debug("Update: {}", document);
		Assert.notNull(document.getDocumentId(), "DocumentId must not be null!");
		Assert.notNull(document.getVersion(), "Version must not be null!");
		return documentRepository
				.findById(document.getDocumentId())
				.defaultIfEmpty(new Document())
				.flatMap(old -> {
					if (old.getDocumentId() == null) {
						throw new RuntimeException("Document does not exists: " + document.getDocumentId());
					}
					return documentRepository.save(getUpdateableDocument(document, old));
				})
				.doOnSuccess(updatedDocument -> notificationSender.sendUpdateSuccess(updatedDocument.getDocumentId()));
	}

	private Document getSaveableDocument(final Document document) {
		Date now = new Date();
		Document newDocument = new Document();
		newDocument.setDocumentId((document.getDocumentId() == null) ? UUID.randomUUID().toString() : document.getDocumentId());
		newDocument.setVersion(null);
		newDocument.setProperties(document.getProperties());
		newDocument.setCreationDate(now);
		newDocument.setLastModificationDate(now);
		return newDocument;
	}

	private Document getUpdateableDocument(final Document document, final Document old) {
		Document newDocument = new Document();
		newDocument.setDocumentId(old.getDocumentId());
		newDocument.setVersion(document.getVersion());
		newDocument.setProperties(document.getProperties());
		newDocument.setCreationDate(old.getCreationDate());
		newDocument.setLastModificationDate(new Date());
		return newDocument;
	}
}
