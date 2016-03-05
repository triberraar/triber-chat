package be.tribersoft.triber.chat.domain.user.api;

import java.util.Set;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

public interface User {

	@NotEmpty(message = "user.validation.password.empty")
	@Length(max = 256, message = "user.validation.password.length")
	String getPassword();

	@NotEmpty(message = "user.validation.roles.empty")
	Set<Role> getRoles();

	@NotEmpty(message = "user.validation.username.empty")
	@Length(max = 256, message = "user.validation.username.length")
	String getUsername();

	@NotEmpty(message = "user.validation.email.empty")
	@Email(message = "user.validation.email.format")
	String getEmail();

}
