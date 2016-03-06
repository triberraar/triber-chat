package be.tribersoft.triber.chat.controller.register;

import be.tribersoft.triber.chat.controller.common.exception.ValidationException;

public class CaptchaVerificationFailedException extends ValidationException {

	public CaptchaVerificationFailedException() {
		super("registration.validation.captcha.failed");
	}

}
