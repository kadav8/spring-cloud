package com.example.notifications;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.messaging.simp.SimpMessageSendingOperations;

import com.example.NotificationMessage;

import reactor.core.publisher.Flux;

@EnableBinding(Sink.class)
public class NotificationListener {
	private Logger log = LoggerFactory.getLogger(NotificationListener.class);

	@Autowired
    private SimpMessageSendingOperations messagingTemplate;

	@StreamListener
	public void incoming(@Input(Sink.INPUT) Flux<NotificationMessage> messages) {
		messages.subscribe(message -> {
			log.info("Incoming notification: " + message);
			messagingTemplate.convertAndSend("/topic/notifications", message);
		});
	}
}
