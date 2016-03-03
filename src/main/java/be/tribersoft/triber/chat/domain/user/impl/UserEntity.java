package be.tribersoft.triber.chat.domain.user.impl;

import java.util.Set;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import be.tribersoft.triber.chat.domain.user.api.Role;
import be.tribersoft.triber.chat.domain.user.api.User;

public class UserEntity implements User {

	@NotNull
	private String username;

	@NotNull
	private String password;

	@NotNull
	private String email;

	@NotEmpty
	private Set<Role> roles;

	public UserEntity(String username, String password, String email, Set<Role> roles) {
		this.username = username;
		this.password = password;
		this.email = email;
		this.roles = roles;
	}

	public String getEmail() {
		return email;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public Set<Role> getRoles() {
		return roles;
	}

	@Override
	public String getUsername() {
		return username;
	}
}
