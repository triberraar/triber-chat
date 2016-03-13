package be.tribersoft.triber.chat.register.service.impl;

import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;

import be.tribersoft.triber.chat.common.WebSocketService;
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
	@Inject
	private WebSocketService webSocketService;

	@Override
	public void register(UserMessage userMessage) {
		User user = userFacade.register(userMessage);
		registerMailService.sendMail(user.getUsername(), user.getId(), user.getEmail());
		webSocketService.send("/topic/notification/registeredUser", user.getUsername());
	}

}
