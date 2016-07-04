package be.tribersoft.triber.chat.message.controller;

import be.tribersoft.triber.chat.message.domain.api.MessageMessage;

public class PrivateMessageFromJsonAdapter implements MessageMessage {
	private String content;
	private String to;
	private String from;

	@Override
	public String getContent() {
		return content;
	}

	public String getTo() {
		return to;
	}

	public String getFrom() {
		return from;
	}
}
