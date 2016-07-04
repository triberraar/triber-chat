package be.tribersoft.triber.chat.message.service.impl;

import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;

import org.springframework.security.access.annotation.Secured;

import be.tribersoft.triber.chat.message.domain.api.PublicMessage;
import be.tribersoft.triber.chat.message.domain.api.MessageFacade;
import be.tribersoft.triber.chat.message.service.api.MessageService;

@Named
@Transactional
public class DefaultMessageService implements MessageService {

	@Inject
	private MessageFacade messageFacade;

	@Override
	@Secured({ "ROLE_ADMIN", "ROLE_USER" })
	public PublicMessage createPublic(String ownerUsername, String content) {
		return messageFacade.createPublic(ownerUsername, content);
	}
}
