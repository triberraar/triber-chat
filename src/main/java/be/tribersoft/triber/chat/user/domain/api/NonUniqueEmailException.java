package be.tribersoft.triber.chat.user.domain.api;

import be.tribersoft.triber.chat.common.exception.ValidationException;

public class NonUniqueEmailException extends ValidationException {

	public NonUniqueEmailException() {
		super("user.validation.email.not.unique");
	}

}
