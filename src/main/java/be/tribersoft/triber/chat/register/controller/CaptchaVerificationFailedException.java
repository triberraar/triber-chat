package be.tribersoft.triber.chat.register.controller;

import be.tribersoft.triber.chat.common.exception.ValidationException;

public class CaptchaVerificationFailedException extends ValidationException {

	public CaptchaVerificationFailedException() {
		super("registration.validation.captcha.failed");
	}

}
