package be.tribersoft.triber.chat.user.domain.api;

import be.tribersoft.triber.chat.common.exception.NotFoundException;

public class UserNotFoundException extends NotFoundException {

	public UserNotFoundException() {
		super("user.not.found.exception");
	}

}
