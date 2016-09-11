package be.tribersoft.triber.chat.user.domain.impl;

import javax.inject.Named;

import com.google.common.collect.Sets;

import be.tribersoft.triber.chat.user.domain.api.Role;
import be.tribersoft.triber.chat.user.domain.api.UserMessage;

@Named
public class UserFactory {

	public UserEntity create(UserMessage userMessage) {
		return new UserEntity(userMessage.getUsername(), userMessage.getPassword(), userMessage.getEmail(), Sets.newHashSet(Role.ROLE_USER));
	}
}
