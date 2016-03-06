package be.tribersoft.triber.chat.domain.user.api;

import be.tribersoft.triber.chat.controller.common.exception.ValidationException;

public class NonUniqueEmailException extends ValidationException {

	public NonUniqueEmailException() {
		super("user.validation.email.not.unique");
	}

}
