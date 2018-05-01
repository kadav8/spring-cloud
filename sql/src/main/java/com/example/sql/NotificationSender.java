package com.example.sql;

public interface NotificationSender {
	public void sendSaveSuccess(String id);
	public void sendUpdateSuccess(String id);
}