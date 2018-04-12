package com.example.messages;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

@EnableBinding(Source.class)
public class MessageSenderService {

	@Autowired
	private Source outputChannelSource;

	public boolean send(Document document, Versioning versioning) {
		Message<DocumentMessage> message = MessageBuilder.withPayload(new DocumentMessage(document, versioning)).build();
		return outputChannelSource.output().send(message);
	}
}

class DocumentMessage {
	private Document document;
	private Versioning versioning;

	public DocumentMessage(Document document, Versioning versioning) {
		this.document = document;
		this.versioning = versioning;
	}

	public Document getDocument() {
		return document;
	}
	public Versioning getVersioning() {
		return versioning;
	}
}

enum Versioning {
	NEW, SAME, UPDATE;
}
