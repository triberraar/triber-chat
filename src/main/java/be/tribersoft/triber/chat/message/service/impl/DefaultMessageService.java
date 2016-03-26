package be.tribersoft.triber.chat.message.service.impl;

import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;

import be.tribersoft.triber.chat.message.domain.api.Message;
import be.tribersoft.triber.chat.message.domain.api.MessageFacade;
import be.tribersoft.triber.chat.message.service.api.MessageService;

@Named
@Transactional
public class DefaultMessageService implements MessageService {

	@Inject
	private MessageFacade messageFacade;

	@Override
	public Message create(String ownerUsername, String content) {
		return messageFacade.createPublic(ownerUsername, content);
	}
}
