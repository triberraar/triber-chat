package be.tribersoft.triber.chat.register.service.api;

import be.tribersoft.triber.chat.user.domain.api.UserMessage;

public interface RegisterService {
	void register(UserMessage userMessage);

}
