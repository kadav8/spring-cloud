package hu.example.redis;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.example.NotificationSender;

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
		Assert.notNull(document.getDocumentId(), "DocumentId must not be null!");
		Assert.notNull(document.getTitle(), "Title must not be null!");
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
		document.setVersion(null);
		document.setCreationDate(now);
		document.setLastModificationDate(now);
		return document;
	}

	private Document getUpdateableDocument(final Document document, final Document old) {
		document.setCreationDate(old.getCreationDate());
		document.setLastModificationDate(new Date());
		return document;
	}
}
