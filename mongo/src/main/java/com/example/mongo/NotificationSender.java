package com.example.mongo;

public interface NotificationSender {
	public void sendSaveSuccess(String id);
	public void sendUpdateSuccess(String id);
}