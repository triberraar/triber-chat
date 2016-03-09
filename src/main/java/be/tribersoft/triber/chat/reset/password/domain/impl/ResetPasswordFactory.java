package be.tribersoft.triber.chat.reset.password.domain.impl;

import javax.inject.Named;

@Named
public class ResetPasswordFactory {

	public ResetPasswordEntity create(String userId) {
		return new ResetPasswordEntity(userId);
	}
}
