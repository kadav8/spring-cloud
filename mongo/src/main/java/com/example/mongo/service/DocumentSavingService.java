package com.example.mongo.service;

import java.util.Date;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
	Logger LOG = LoggerFactory.getLogger(DocumentSavingService.class);

	@Autowired
	private DocumentRepository documentRepository;
	@Autowired
	private DocumentTypeRepository documentTypeRepository;

	public Mono<Document> storeDocument(Document document) {
		return saveDocument(document, Versioning.NEW);
	}

	public Mono<Document> updateDocument(Document document) {
		return saveDocument(document, Versioning.UPDATE);
	}

	public Mono<Document> updateSameDocument(Document document) {
		return saveDocument(document, Versioning.SAME);
	}

	private Mono<Document> saveDocument(Document document, Versioning versioning) {
		LOG.debug("Save: {}", document);

		Mono<DocumentType> documentTypePublisher = documentTypeRepository
				.findById(document.getType())
				.doOnError(t -> LOG.error("Save document failed", t))
				.doOnSuccess(documentType -> {
					LOG.debug("DocumentType found: {}", documentType);
					if(documentType == null) {
						throw new RuntimeException("DocumentType not exists: " + document.getType());
					}
				});

		Mono<Document> documentPublisher = documentTypePublisher
				.flatMap(documentType -> {
					LOG.debug("Check properties for DocumentType: {}", document.getType());
					checkTypes(document.getStringProperties(), documentType, "string");
					checkTypes(document.getLongProperties(), documentType, "long");
					checkTypes(document.getDoubleProperties(), documentType, "double");
					checkTypes(document.getDateProperties(), documentType, "date");
					setDefaultsAndDoVersioning(document, versioning);
					return documentRepository.save(document);
				});

		return documentPublisher;
	}

	private void checkTypes(Map<String, ?> properties, DocumentType documentType, String expectedType) {
		if(properties != null) {
			properties.keySet().forEach(propname -> {
				String type = documentType.getPropertyDefinitions().get(propname);
				if (type == null || !type.equalsIgnoreCase(expectedType)) {
					throw new RuntimeException("Invalid property: " + propname);
				}
			});
		}
	}

	private void setDefaultsAndDoVersioning(Document document, Versioning versioning) {
		Date now = new Date();
		document.setIsCheckedOut(false);

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
	}
}
