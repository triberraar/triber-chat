package be.tribersoft.triber.chat.message.controller;

import java.util.Date;

import be.tribersoft.triber.chat.message.domain.api.Message;

public class MessageToJsonAdapter {

	private String content;
	private String username;
	private Date timestamp;

	protected MessageToJsonAdapter() {

	}

	public MessageToJsonAdapter(Message message) {
		this.content = message.getContent();
		this.username = message.getOwnerUsername();
		this.timestamp = message.getCreationDate();
	}

	public String getContent() {
		return content;
	};

	public String getUsername() {
		return username;
	}

	public Date getTimestamp() {
		return timestamp;
	}
}
