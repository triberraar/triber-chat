package be.tribersoft.triber.chat.reset.password.domain.api;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

public interface ResetPasswordMessage {

	@Email(message = "reset_password.validation.email.format")
	@NotEmpty(message = "reset_password.validation.email.empty")
	public String getEmail();

}
