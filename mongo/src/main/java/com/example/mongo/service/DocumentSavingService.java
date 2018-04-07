package com.example.mongo.service;

import java.util.Date;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.example.mongo.domain.Document;
import com.example.mongo.domain.DocumentType;
import com.example.mongo.domain.Versioning;
import com.example.mongo.repository.DocumentRepository;
import com.example.mongo.repository.DocumentTypeRepository;

import reactor.core.publisher.Mono;

@Service
public class DocumentSavingService {
	private Logger LOG = LoggerFactory.getLogger(DocumentSavingService.class);

	private final DocumentRepository documentRepository;
	private final DocumentTypeRepository documentTypeRepository;

	public DocumentSavingService(DocumentRepository documentRepository, DocumentTypeRepository documentTypeRepository) {
		this.documentRepository = documentRepository;
		this.documentTypeRepository = documentTypeRepository;
	}

	public Mono<Document> saveDocument(final DocumentMessage documentMessage) {
		return saveDocument(documentMessage.getDocument(), documentMessage.getVersioning());
	}

	public Mono<Document> saveDocument(final Document document, final Versioning versioning) {
		LOG.debug("Save: {}", document);
		return documentTypeRepository
				.findById(document.getType())
				.doOnError(t -> LOG.error("Save document failed", t))
				.doOnSuccess(documentType -> checkDocumentType(documentType, document))
				.map(documentType -> getSaveableDocument(document, versioning))
				.flatMap(saveableDocument -> documentRepository.save(saveableDocument));
	}

	private void checkDocumentType(final DocumentType documentType, final Document document) {
		if(documentType != null) {
			LOG.debug("Check properties for DocumentType: {}", document.getType());
			checkTypes(document.getStringProperties(), documentType, "string");
			checkTypes(document.getLongProperties(), documentType, "long");
			checkTypes(document.getDoubleProperties(), documentType, "double");
			checkTypes(document.getDateProperties(), documentType, "date");
		} else {
			throw new RuntimeException("DocumentType not exists: " + document.getType());
		}
	}
	private void checkTypes(final Map<String, ?> properties, final DocumentType documentType, final String expectedType) {
		if(properties != null) {
			properties.keySet().forEach(propname -> {
				String type = documentType.getPropertyDefinitions().get(propname);
				if (type == null || !type.equalsIgnoreCase(expectedType)) {
					throw new RuntimeException("Invalid property: " + propname);
				}
			});
		}
	}

	private Document getSaveableDocument(final Document document, final Versioning versioning) {
		// TODO: new document
		Date now = new Date();
		if(Versioning.NEW.equals(versioning)) {
			document.setDocumentId((document.getDocumentId() == null)
					? UUID.randomUUID().toString() : document.getDocumentId());
			document.setVersionSeriesId((document.getVersionSeriesId() == null)
					? UUID.randomUUID().toString() : document.getVersionSeriesId());
			document.setCreationDate(now);
		}
		else if (Versioning.UPDATE.equals(versioning)) {
			Assert.notNull(document.getVersionSeriesId(), "VersionSeriesId must not be null!");
			document.setDocumentId((document.getDocumentId() == null)
					? UUID.randomUUID().toString() : document.getDocumentId());
			document.setCreationDate(now);
		}
		else if (Versioning.SAME.equals(versioning)) {
			Assert.notNull(document.getDocumentId(), "DocumentId must not be null!");
			Assert.notNull(document.getVersionSeriesId(), "VersionSeriesId must not be null!");
			document.setLastModificationDate(now);
		}
		return document;
	}
}
