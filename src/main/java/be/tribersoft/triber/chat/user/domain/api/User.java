package be.tribersoft.triber.chat.user.domain.api;

import java.util.Set;

import org.hibernate.validator.constraints.NotEmpty;

public interface User extends UserMessage {

	@Override
	String getPassword();

	@NotEmpty(message = "user.validation.roles.empty")
	Set<Role> getRoles();

	@Override
	String getUsername();

	@Override
	String getEmail();

	String getId();

}
