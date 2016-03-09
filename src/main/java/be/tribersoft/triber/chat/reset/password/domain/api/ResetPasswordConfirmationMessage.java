package be.tribersoft.triber.chat.reset.password.domain.api;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

public interface ResetPasswordConfirmationMessage {

	@NotEmpty(message = "reset.password.validation.password.empty")
	@Length(max = 256, min = 6, message = "reset.password.validation.password.length")
	String getPassword();

}
