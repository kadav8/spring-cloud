package com.example.sql;

import java.util.Date;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

@Service
public class DocumentService {
	private Logger LOG = LoggerFactory.getLogger(DocumentService.class);

	private final DocumentRepository documentRepository;
	private final NotificationSender notificationSender;

	public DocumentService(DocumentRepository documentRepository, NotificationSender notificationSender) {
		this.documentRepository = documentRepository;
		this.notificationSender = notificationSender;
	}

	@Transactional
	public Document saveDocument(final Document document) {
		LOG.debug("Save: {}", document);
		Assert.notNull(document.getDocumentId(), "DocumentId must not be null!");
		Assert.notNull(document.getTitle(), "Title must not be null!");
		Assert.isTrue(!documentRepository.existsById(document.getDocumentId()), "Document already exists: " + document.getDocumentId());
		Document savedDocument = documentRepository.save(getSaveableDocument(document));
		notificationSender.sendSaveSuccess(savedDocument.getDocumentId());
		return savedDocument;
	}

	public Document updateDocument(final Document document) {
		LOG.debug("Update: {}", document);
		Assert.notNull(document.getDocumentId(), "DocumentId must not be null!");
		Assert.notNull(document.getTitle(), "Title must not be null!");
		Assert.notNull(document.getVersion(), "Version must not be null!");
		Optional<Document> old = documentRepository.findById(document.getDocumentId());
		Assert.isTrue(old.isPresent(), "Document does not exists: " + document.getDocumentId());
		Assert.isTrue(old.get().getVersion().equals(document.getVersion()), "Versions must be equal!");
		Document updatedDocument = documentRepository.save(getUpdateableDocument(document, old.get()));
		notificationSender.sendUpdateSuccess(updatedDocument.getDocumentId());
		return updatedDocument;
	}

	private Document getSaveableDocument(final Document document) {
		Date now = new Date();
		document.setVersion(0L);
		document.setCreationDate(now);
		document.setLastModificationDate(now);
		return document;
	}

	private Document getUpdateableDocument(final Document document, final Document old) {
		document.setCreationDate(old.getCreationDate());
		document.setLastModificationDate(new Date());
		document.setVersion(old.getVersion()+1);
		return document;
	}
}
