package com.example.messages;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.support.MessageBuilder;

@EnableBinding(Source.class)
public class MessageSenderService {

	@Autowired
	private Source outputChannelSource;

	public void send(Document document) {
		outputChannelSource.output().send(MessageBuilder.withPayload(document).build());
	}
}
