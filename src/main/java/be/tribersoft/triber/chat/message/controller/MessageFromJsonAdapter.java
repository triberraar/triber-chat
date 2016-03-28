package be.tribersoft.triber.chat.message.controller;

import be.tribersoft.triber.chat.message.domain.api.MessageMessage;

public class MessageFromJsonAdapter implements MessageMessage {

	private String content;

	@Override
	public String getContent() {
		return content;
	}
}
