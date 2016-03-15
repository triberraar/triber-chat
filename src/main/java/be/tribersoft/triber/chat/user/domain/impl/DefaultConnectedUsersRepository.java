package be.tribersoft.triber.chat.user.domain.impl;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.inject.Named;

import be.tribersoft.triber.chat.user.domain.api.ConnectedUsersRepository;
import be.tribersoft.triber.chat.user.domain.api.User;

@Named
public class DefaultConnectedUsersRepository implements ConnectedUsersRepository {

	private Map<String, User> concurrentUsers = new ConcurrentHashMap<>();

	@Override
	public void addUser(User user) {
		concurrentUsers.put(user.getId(), user);
	}

	@Override
	public void removeUser(User user) {
		concurrentUsers.remove(user.getId());
	}

	@Override
	public Collection<User> findAll() {
		return concurrentUsers.values();
	}

}
