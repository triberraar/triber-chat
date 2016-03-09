package be.tribersoft.triber.chat.reset.password.controller;

import be.tribersoft.triber.chat.reset.password.domain.api.ResetPasswordMessage;

public class ResetPasswordRequestFromJsonAdapter implements ResetPasswordMessage {

	private String email;

	@Override
	public String getEmail() {
		return email;
	}

}
