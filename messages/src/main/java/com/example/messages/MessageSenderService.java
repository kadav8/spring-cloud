package com.example.messages;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;

@EnableBinding(Source.class)
public class MessageSenderService {

	@Autowired
	private Source outputChannelSource;

	public void send(Document document) {
		MessageChannel channel = outputChannelSource.output();
		channel.send(MessageBuilder.withPayload(document).build());
	}
}
