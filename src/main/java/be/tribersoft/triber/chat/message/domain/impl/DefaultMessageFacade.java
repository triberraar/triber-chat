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
	public PublicMessageEntity createPublic(String ownerId, String content) {
		PublicMessageEntity messageEntity = messageFactory.createPublic(ownerId, content);
		defaultMessageRepository.save(messageEntity);
		return messageEntity;
	}
}
