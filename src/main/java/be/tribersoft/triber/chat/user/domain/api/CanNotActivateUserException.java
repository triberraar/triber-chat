package be.tribersoft.triber.chat.user.domain.api;

import be.tribersoft.triber.chat.common.exception.BusinessException;

public class CanNotActivateUserException extends BusinessException {
	public CanNotActivateUserException() {
		super("user.can.not.activate");
	}
}
