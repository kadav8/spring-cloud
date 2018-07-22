package com.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableBinding(NotificationSource.class)
@EnableAsync
public class RabbitNotificationSender implements NotificationSender {
	private Logger LOG = LoggerFactory.getLogger(RabbitNotificationSender.class);

	@Autowired
	private NotificationSource outputChannelSource;

	@Override
	@Async
	public void sendSaveSuccess(String id) {
		sendSuccess("New document added to MongoDB: " + id);
	}

	@Override
	@Async
	public void sendUpdateSuccess(String id) {
		sendSuccess("Document updated in MongoDB: " + id);
	}

	private boolean sendSuccess(String text) {
		LOG.info("Send notification");
		NotificationMessage notmessage = new NotificationMessage();
		notmessage.setAppname("Mongo");
		notmessage.setText(text);
		notmessage.setType("success");
		Message<NotificationMessage> message = MessageBuilder.withPayload(notmessage).build();
		return outputChannelSource.output().send(message);
	}
}

interface NotificationSource {
	String OUTPUT = "notification_output";
	@Output(NotificationSource.OUTPUT)
	MessageChannel output();
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
