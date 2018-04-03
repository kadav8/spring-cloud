package com.example.mongo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;

import com.example.mongo.domain.Document;

import reactor.core.publisher.Flux;

@EnableBinding(Sink.class)
public class DocumentListenerSevice {
	private Logger LOG = LoggerFactory.getLogger(DocumentSavingService.class);

	@Autowired
	private DocumentSavingService documentSavingService;

	@StreamListener
	public void incoming(@Input(Sink.INPUT) Flux<Document> documents) {
		documents
				.flatMap(this.documentSavingService::storeDocument)
				.subscribe(d -> LOG.info("Saved: {}" , d));
	}
}
