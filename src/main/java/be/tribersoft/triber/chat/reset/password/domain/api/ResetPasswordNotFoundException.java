package be.tribersoft.triber.chat.reset.password.domain.api;

import be.tribersoft.triber.chat.common.exception.NotFoundException;

public class ResetPasswordNotFoundException extends NotFoundException {

	public ResetPasswordNotFoundException() {
		super("reset_password.not.found.exception");
	}

}
