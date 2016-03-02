package be.tribersoft.triber.chat.configuration;

import javax.inject.Named;

import be.tribersoft.triber.chat.domain.User;

// use jwt here.
@Named
public class TokenHandler {

	public User fromToken(String token) {
		return new User(token);
	}

	public String toToken(User user) {
		return user.getUsername();
	}

}
