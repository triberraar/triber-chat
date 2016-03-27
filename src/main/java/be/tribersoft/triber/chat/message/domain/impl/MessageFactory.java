package be.tribersoft.triber.chat.message.domain.impl;

import javax.inject.Named;

@Named
public class MessageFactory {

	public PublicMessageEntity createPublic(String ownerUsername, String content) {
		return new PublicMessageEntity(ownerUsername, content);
	}
}
