package be.tribersoft.triber.chat.service.register.impl;

import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;

import be.tribersoft.triber.chat.domain.user.api.UserFacade;
import be.tribersoft.triber.chat.domain.user.api.UserMessage;
import be.tribersoft.triber.chat.service.register.api.RegisterService;

@Named
@Transactional
public class DefaultRegisterService implements RegisterService {

	@Inject
	private UserFacade userFacade;

	@Override
	public void register(UserMessage userMessage) {
		userFacade.register(userMessage);
	}

}
