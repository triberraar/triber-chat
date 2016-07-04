package be.tribersoft.triber.chat.message.controller;

import be.tribersoft.triber.chat.message.domain.api.MessageMessage;

public class PrivateMessageFromJsonAdapter implements MessageMessage {
	private String content;
	private String destination;

	@Override
	public String getContent() {
		return content;
	}

	public String getDestination() {
		return destination;
	}
}
