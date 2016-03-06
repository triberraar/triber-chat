package be.tribersoft.triber.chat.controller.common.exception;

public class NotFoundException extends BusinessException {

	public NotFoundException(String errorCode) {
		super(errorCode);
	}

}