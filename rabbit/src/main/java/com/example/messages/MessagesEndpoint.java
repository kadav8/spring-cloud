package com.example.messages;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MessagesEndpoint {
	private static final Logger log = LoggerFactory.getLogger(MessagesEndpoint.class);

	private final MessageSenderService senderService;

	public MessagesEndpoint(MessageSenderService senderService) {
		this.senderService = senderService;
	}

	@PostMapping("/store")
	public void storeDocument(@RequestBody Document document) {
		log.info("Store document: {}", document);
		senderService.send(document, Versioning.NEW);
	}

	@PostMapping("/update")
	public void updateDocument(@RequestBody Document document) {
		log.info("Update document: {}", document);
		senderService.send(document, Versioning.UPDATE);
	}

	@PostMapping("/sameUpdate")
	public void updateSameDocument(@RequestBody Document document) {
		log.info("Update same document: {}", document);
		senderService.send(document, Versioning.SAME);
	}
}
