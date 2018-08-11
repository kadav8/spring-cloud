package com.example;

public class NotificationMessage {
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
