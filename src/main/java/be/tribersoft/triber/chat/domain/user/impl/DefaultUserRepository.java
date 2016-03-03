package be.tribersoft.triber.chat.domain.user.impl;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.inject.Named;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import be.tribersoft.triber.chat.domain.user.api.Role;
import be.tribersoft.triber.chat.domain.user.api.UserRepository;

@Named
public class DefaultUserRepository implements UserRepository {
	Map<String, UserEntity> users = new HashMap<>();

	@PostConstruct
	public void init() {
		users.put("geert", new UserEntity("geert", new BCryptPasswordEncoder().encode("password"), "geertolaerts@gmail.com", new HashSet<>(Arrays.asList(Role.ADMIN, Role.USER))));
		users.put("user", new UserEntity("user", new BCryptPasswordEncoder().encode("password"), "user@gmail.com", new HashSet<>(Arrays.asList(Role.USER))));
	}

	@Override
	public Optional<UserEntity> findByUsername(String username) {
		return Optional.ofNullable(users.get(username));
	}
}
