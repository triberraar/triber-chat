package be.tribersoft.triber.chat.common;

import javax.inject.Named;

import org.springframework.context.annotation.Primary;

import be.tribersoft.triber.chat.register.controller.CaptchaVerificator;

@Named
@Primary
public class DummyCaptchaVerificator extends CaptchaVerificator {

	private boolean called = false;
	private String captcha = null;

	@Override
	public void verify(String captcha) {
		this.captcha = captcha;
		called = true;
	}

	public synchronized boolean isCalled() {
		boolean temp = called;
		called = false;
		return temp;
	}

	public synchronized String getCaptcha() {
		String temp = captcha;
		captcha = null;
		return temp;
	}
}
