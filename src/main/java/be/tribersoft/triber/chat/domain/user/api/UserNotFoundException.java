package be.tribersoft.triber.chat.domain.user.api;

import be.tribersoft.triber.chat.controller.common.exception.NotFoundException;

public class UserNotFoundException extends NotFoundException {

	public UserNotFoundException() {
		super("user.not.found.exception");
	}

}
