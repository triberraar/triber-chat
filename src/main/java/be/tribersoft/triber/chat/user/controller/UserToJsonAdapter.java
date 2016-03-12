package be.tribersoft.triber.chat.user.controller;

import be.tribersoft.triber.chat.user.domain.api.User;

public class UserToJsonAdapter {

	private User user;

	public UserToJsonAdapter(User user) {
		this.user = user;
	}

	public String getId() {
		return user.getId();
	}

	public String getUsername() {
		return user.getUsername();
	}

	public String getEmail() {
		return user.getEmail();
	}

	public boolean isActivated() {
		return user.isActivated();
	}

	public boolean isValidated() {
		return user.isValidated();
	}
}
