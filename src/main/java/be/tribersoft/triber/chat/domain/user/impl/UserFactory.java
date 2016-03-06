package be.tribersoft.triber.chat.domain.user.impl;

import java.util.Arrays;
import java.util.HashSet;

import javax.inject.Named;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import be.tribersoft.triber.chat.domain.user.api.Role;
import be.tribersoft.triber.chat.domain.user.api.UserMessage;

@Named
public class UserFactory {

	public UserEntity create(UserMessage userMessage) {
		String password = new BCryptPasswordEncoder().encode(userMessage.getPassword());
		return new UserEntity(userMessage.getUsername(), password, userMessage.getEmail(), new HashSet<>(Arrays.asList(Role.USER)));
	}
}
