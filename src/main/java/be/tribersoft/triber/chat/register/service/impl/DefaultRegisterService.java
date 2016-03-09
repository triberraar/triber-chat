package be.tribersoft.triber.chat.register.service.impl;

import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;

import org.springframework.security.access.annotation.Secured;

import be.tribersoft.triber.chat.register.service.api.RegisterService;
import be.tribersoft.triber.chat.user.domain.api.User;
import be.tribersoft.triber.chat.user.domain.api.UserFacade;
import be.tribersoft.triber.chat.user.domain.api.UserMessage;

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

	@Override
	@Secured({ "ROLE_ADMIN" })
	public void validate(String userId) {
		userFacade.validate(userId);
	}

}
