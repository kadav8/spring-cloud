package com.example.notifications;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.messaging.simp.SimpMessageSendingOperations;

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

class NotificationMessage {
	private String appname;
	private String text;
	private String type;

	public NotificationMessage() {}
	public NotificationMessage(String appname, String text, String type) {
		this.appname = appname;
		this.text = text;
		this.type = type;
	}

	public String getAppname() {
		return appname;
	}
	public void setAppname(String appname) {
		this.appname = appname;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "NotificationMessage [appname=" + appname + ", text=" + text + ", type=" + type + "]";
	}
}