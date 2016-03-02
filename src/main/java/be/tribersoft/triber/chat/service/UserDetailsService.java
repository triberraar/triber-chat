package be.tribersoft.triber.chat.service;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.inject.Named;

import org.springframework.security.core.userdetails.UsernameNotFoundException;

import be.tribersoft.triber.chat.domain.User;

@Named
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

	private Map<String, User> users = new HashMap<>();

	@PostConstruct
	public void create() {
		users.put("geert", new User("geert", "password"));
		users.put("user2", new User("user2", "password"));
	}

	@Override
	public User loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = users.get(username);
		if (user == null) {
			throw new UsernameNotFoundException("not found");
		}
		return user;
	}

}
