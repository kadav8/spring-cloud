package com.example.mongo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;

import com.example.mongo.domain.Document;
import com.example.mongo.domain.Versioning;

import reactor.core.publisher.Flux;

@EnableBinding(Sink.class)
public class DocumentListenerSevice {
	private Logger LOG = LoggerFactory.getLogger(DocumentSavingService.class);

	private final DocumentSavingService documentSavingService;

	public DocumentListenerSevice(DocumentSavingService documentSavingService) {
		this.documentSavingService = documentSavingService;
	}

	@StreamListener
	public void incoming(@Input(Sink.INPUT) Flux<DocumentMessage> documentMessages) {
		documentMessages
				.flatMap(this.documentSavingService::saveDocument)
				.subscribe(d -> LOG.info("Saved: {}" , d));
	}
}

class DocumentMessage {
	private Document document;
	private Versioning versioning;

	public Document getDocument() {
		return document;
	}
	public void setDocument(Document document) {
		this.document = document;
	}
	public Versioning getVersioning() {
		return versioning;
	}
	public void setVersioning(Versioning versioning) {
		this.versioning = versioning;
	}
}