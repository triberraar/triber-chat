package be.tribersoft.triber.chat.user.domain.api;

public interface UserFacade {

	User register(UserMessage userMessage);

	void activate(String id, String password);

	void changePassword(String id, String password);

	void validate(String id);
}
