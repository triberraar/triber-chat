package be.tribersoft.triber.chat.user.domain.api;

import be.tribersoft.triber.chat.common.exception.ValidationException;

public class NonUniqueUsernameException extends ValidationException {

	public NonUniqueUsernameException() {
		super("user.validation.username.not.unique");
	}

}
