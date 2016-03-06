package be.tribersoft.triber.chat.domain.user.api;

public interface UserFacade {

	User register(UserMessage userMessage);

	void activate(String userId, String password);
}
