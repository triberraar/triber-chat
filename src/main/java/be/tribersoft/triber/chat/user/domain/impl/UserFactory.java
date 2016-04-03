package be.tribersoft.triber.chat.user.domain.impl;

import java.util.Arrays;
import java.util.HashSet;

import javax.inject.Named;

import be.tribersoft.triber.chat.user.domain.api.Role;
import be.tribersoft.triber.chat.user.domain.api.UserMessage;

@Named
public class UserFactory {

	public UserEntity create(UserMessage userMessage) {
		return new UserEntity(userMessage.getUsername(), userMessage.getPassword(), userMessage.getEmail(), new HashSet<>(Arrays.asList(Role.ROLE_USER)));
	}
}
