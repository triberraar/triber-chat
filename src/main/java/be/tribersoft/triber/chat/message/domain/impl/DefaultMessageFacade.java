package be.tribersoft.triber.chat.message.domain.impl;

import javax.inject.Inject;
import javax.inject.Named;

import be.tribersoft.triber.chat.message.domain.api.MessageFacade;

@Named
public class DefaultMessageFacade implements MessageFacade {
	@Inject
	private MessageFactory messageFactory;
	@Inject
	private DefaultMessageRepository defaultMessageRepository;

	@Override
	public PublicMessageEntity createPublic(String ownerUsername, String content) {
		PublicMessageEntity messageEntity = messageFactory.createPublic(ownerUsername, content);
		defaultMessageRepository.save(messageEntity);
		return messageEntity;
	}

	public PrivateMessageEntity createPrivate(String content, String to, String from) {
		PrivateMessageEntity messageEntity = messageFactory.createPrivate(content, to, from);
		defaultMessageRepository.save(messageEntity);
		return messageEntity;
	}
}
