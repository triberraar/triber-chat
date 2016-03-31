package be.tribersoft.triber.chat.user.domain.impl;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.HashSet;

import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import be.tribersoft.triber.chat.user.domain.api.CanNotActivateUserException;
import be.tribersoft.triber.chat.user.domain.api.Role;

public class UserEntityActivateTest {

	@Test
	public void activatesIfPasswordMatches() {
		UserEntity user = new UserEntity("username", new BCryptPasswordEncoder().encode("password"), "email@email", new HashSet<>(Arrays.asList(Role.ROLE_ADMIN)));

		user.activate("password");

		assertThat(user.isActivated()).isTrue();
	}

	@Test(expected = CanNotActivateUserException.class)
	public void failsWhenPasswordDoesntMatch() {
		UserEntity user = new UserEntity("username", new BCryptPasswordEncoder().encode("password"), "email@email", new HashSet<>(Arrays.asList(Role.ROLE_ADMIN)));

		user.activate("password2");
	}
}
