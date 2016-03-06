package be.tribersoft.triber.chat.service.register.api;

import be.tribersoft.triber.chat.domain.user.api.UserMessage;

public interface RegisterService {
	void register(UserMessage userMessage);
}
