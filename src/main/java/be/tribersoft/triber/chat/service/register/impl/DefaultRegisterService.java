package be.tribersoft.triber.chat.service.register.impl;

import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;

import be.tribersoft.triber.chat.domain.user.api.User;
import be.tribersoft.triber.chat.domain.user.api.UserFacade;
import be.tribersoft.triber.chat.domain.user.api.UserMessage;
import be.tribersoft.triber.chat.service.register.api.RegisterService;

@Named
@Transactional
public class DefaultRegisterService implements RegisterService {

	@Inject
	private UserFacade userFacade;
	@Inject
	private RegisterMailService registerMailService;

	@Override
	public void register(UserMessage userMessage) {
		User user = userFacade.register(userMessage);
		registerMailService.sendMail(user.getUsername(), user.getId(), user.getEmail());
	}

	@Override
	public void activate(String userId, String password) {
		userFacade.activate(userId, password);
	}

}
