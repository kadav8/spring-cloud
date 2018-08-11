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
