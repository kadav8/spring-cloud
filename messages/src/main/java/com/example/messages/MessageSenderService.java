package com.example.messages;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.handler.annotation.SendTo;

@EnableBinding(Source.class)
public class MessageSenderService {

	@Autowired
	private Source outputChannelSource;

	@SendTo(Source.OUTPUT)
	public Document send(Document document) {
		return document;
		//MessageChannel channel = outputChannelSource.output();
		//channel.send(MessageBuilder.withPayload(document).build());
	}
}
