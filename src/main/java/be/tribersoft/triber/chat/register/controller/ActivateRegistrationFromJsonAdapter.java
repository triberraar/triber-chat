package be.tribersoft.triber.chat.register.controller;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

public class ActivateRegistrationFromJsonAdapter {
	private String password;

	@NotEmpty(message = "activate.registration.validation.password.empty")
	@Length(max = 256, min = 6, message = "activate.registration.validation.password.length")
	public String getPassword() {
		return password;
	}
}
