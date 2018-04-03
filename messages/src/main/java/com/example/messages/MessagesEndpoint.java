package com.example.messages;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MessagesEndpoint {
	Logger log = LoggerFactory.getLogger(MessagesEndpoint.class);

	@Autowired
	private MessageSenderService senderService;

	@GetMapping("/test")
	public String test( ) {
		log.info("test message");
		Document doc = new Document();
		doc.setType("SimpleDocument");
		senderService.send(doc);
		return "ok";
	}
}
