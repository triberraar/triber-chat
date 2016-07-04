package be.tribersoft.triber.chat.message.controller;

import java.util.Date;

import be.tribersoft.triber.chat.message.domain.api.PrivateMessage;

public class PrivateMessageToJsonAdapter {
	private String content;
	private String to;
	private String from;
	private Date timestamp;

	protected PrivateMessageToJsonAdapter() {
	}

	public PrivateMessageToJsonAdapter(PrivateMessage message) {
		this.content = message.getContent();
		this.to = message.getTo();
		this.from = message.getFrom();
		this.timestamp = message.getCreationDate();
	}

	public String getContent() {
		return content;
	}

	public String getTo() {
		return to;
	}

	public String getFrom() {
		return from;
	}

	public Date getTimestamp() {
		return timestamp;
	}

}
