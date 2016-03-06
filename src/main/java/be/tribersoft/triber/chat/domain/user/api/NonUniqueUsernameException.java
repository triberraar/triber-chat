package be.tribersoft.triber.chat.domain.user.api;

import be.tribersoft.triber.chat.controller.common.exception.ValidationException;

public class NonUniqueUsernameException extends ValidationException {

	public NonUniqueUsernameException() {
		super("user.validation.username.not.unique");
	}

}
