package com.example.mongo.service;

import java.util.Date;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.mongo.domain.Document;
import com.example.mongo.domain.DocumentType;
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

	public Mono<Document> saveDocument(Document document) {
		LOG.debug("Save: {}", document);

		Mono<DocumentType> documentTypePublisher = documentTypeRepository
				.findById(document.getDataType())
				.doOnError(t -> LOG.error("Save document failed", t))
				.doOnSuccess(documentType -> {
					LOG.debug("DocumentType found: {}", documentType);
					if(documentType == null) {
						throw new RuntimeException("DocumentType not exists: " + document.getDataType());
					}
				});

		Mono<Document> documentPublisher = documentTypePublisher
				.flatMap(documentType -> {
					LOG.debug("Check properties for DocumentType: {}", document.getDataType());
					checkTypes(document.getStringProperties(), documentType, "string");
					checkTypes(document.getLongProperties(), documentType, "long");
					checkTypes(document.getDoubleProperties(), documentType, "double");
					checkTypes(document.getDateProperties(), documentType, "date");
					return documentRepository.save(withDefaults(document));
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

	private Document withDefaults(Document document) {
		Date now = new Date();
		if(document.getDocumentId() == null) {
			document.setDocumentId(UUID.randomUUID().toString());
		}
		if(document.getVersionSeriesId() == null) {
			document.setVersionSeriesId(UUID.randomUUID().toString());
		}
		document.setMajorVersion(1);
		document.setMinorVersion(0);
		document.setCreationDate(now);
		document.setLastModifiedDate(now);
		document.setIsCheckedOut(false);
		return document;
	}
}
