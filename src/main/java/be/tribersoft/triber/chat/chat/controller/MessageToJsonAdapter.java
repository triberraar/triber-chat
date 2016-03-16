package be.tribersoft.triber.chat.chat.controller;

import java.util.Date;

public class MessageToJsonAdapter {

	private String message;
	private String username;
	private Date timestamp;

	public MessageToJsonAdapter(String message, String username, Date timestamp) {
		this.message = message;
		this.username = username;
		this.timestamp = timestamp;
	}

	public String getMessage() {
		return message;
	}

	public String getUsername() {
		return username;
	}

	public Date getTimestamp() {
		return timestamp;
	}
}
