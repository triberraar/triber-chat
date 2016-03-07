package be.tribersoft.triber.chat.reset.password.controller;

import be.tribersoft.triber.chat.reset.password.domain.api.ResetPasswordConfirmationMessage;

public class ResetPasswordConfirmationFromJsonAdapter implements ResetPasswordConfirmationMessage {

	private String password;

	@Override
	public String getPassword() {
		return password;
	}

}
