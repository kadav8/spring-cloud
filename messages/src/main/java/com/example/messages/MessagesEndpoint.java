package com.example.messages;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MessagesEndpoint {
	Logger log = LoggerFactory.getLogger(MessagesEndpoint.class);

	@Autowired
	private MessageSenderService senderService;

	@PostMapping("/store")
	public void storeDocument(@RequestBody Document document) {
		log.info("Store document: {}", document);
		senderService.send(document);
	}
}
