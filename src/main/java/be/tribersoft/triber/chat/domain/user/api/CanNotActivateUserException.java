package be.tribersoft.triber.chat.domain.user.api;

import be.tribersoft.triber.chat.controller.common.exception.BusinessException;

public class CanNotActivateUserException extends BusinessException {
	public CanNotActivateUserException() {
		super("user.can.not.activate");
	}
}
