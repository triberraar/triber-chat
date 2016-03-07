package be.tribersoft.triber.chat.user.domain.api;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

public interface UserMessage {

	@NotEmpty(message = "user.validation.email.empty")
	@Email(message = "user.validation.email.format")
	public String getEmail();

	@NotEmpty(message = "user.validation.password.empty")
	@Length(max = 256, min = 6, message = "user.validation.password.length")
	public String getPassword();

	@NotEmpty(message = "user.validation.username.empty")
	@Length(max = 256, min = 4, message = "user.validation.username.length")
	public String getUsername();
}
