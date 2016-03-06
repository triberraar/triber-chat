package be.tribersoft.triber.chat.controller.register;

import org.hibernate.validator.constraints.NotEmpty;

import be.tribersoft.triber.chat.domain.user.api.UserMessage;

public class RegisterFromJsonAdapter implements UserMessage {

	private String username;
	private String email;
	private String captcha;
	private String password;

	@NotEmpty(message = "register.validation.captcha.empty")
	public String getCaptcha() {
		return captcha;
	}

	@Override
	public String getEmail() {
		return email;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
	}
}
