package be.tribersoft.triber.chat.user.domain.impl;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.HashSet;

import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import be.tribersoft.triber.chat.user.domain.api.Role;

public class UserEntityChangePasswordTest {

	private static final String NEW_PASSWORD = "new password";

	@Test
	public void setsAndEncodesPassword() {
		UserEntity user = new UserEntity("username", "password", "email@email", new HashSet<>(Arrays.asList(Role.ROLE_USER)));

		user.changePassword(NEW_PASSWORD);

		assertThat(new BCryptPasswordEncoder().matches(NEW_PASSWORD, user.getPassword())).isTrue();
	}

}
