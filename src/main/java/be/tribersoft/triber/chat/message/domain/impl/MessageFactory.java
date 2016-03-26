package be.tribersoft.triber.chat.message.domain.impl;

import javax.inject.Named;

@Named
public class MessageFactory {

	public MessageEntity createPublic(String ownerUsername, String content) {
		return new MessageEntity(ownerUsername, content, true);
	}
}
