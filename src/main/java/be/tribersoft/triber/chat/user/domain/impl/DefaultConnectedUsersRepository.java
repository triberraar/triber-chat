package be.tribersoft.triber.chat.user.domain.impl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import javax.inject.Named;

import be.tribersoft.triber.chat.user.domain.api.ConnectedUsersRepository;
import be.tribersoft.triber.chat.user.domain.api.User;

@Named
public class DefaultConnectedUsersRepository implements ConnectedUsersRepository {

	private Map<String, List<User>> concurrentUsers = new ConcurrentHashMap<>();

	@Override
	public synchronized void addUser(User user) {
		if (concurrentUsers.containsKey(user.getId())) {
			concurrentUsers.get(user.getId()).add(user);
		} else {
			List<User> UserInstances = new ArrayList<>();
			UserInstances.add(user);
			concurrentUsers.put(user.getId(), UserInstances);
		}
	}

	@Override
	public synchronized void removeUser(User user) {
		if (!concurrentUsers.containsKey(user.getId())) {
			return;
		}
		concurrentUsers.get(user.getId()).remove(0);
		if (concurrentUsers.get(user.getId()).isEmpty()) {
			concurrentUsers.remove(user.getId());
		}
	}

	@Override
	public synchronized List<User> findAll() {
		Comparator<User> comparator = Comparator.comparing(User::getUsernameLowerCase);
		return concurrentUsers.values().stream().map(users -> users.get(0)).sorted(comparator).collect(Collectors.toList());
	}

	@Override
	public synchronized boolean exists(String username) {
		return concurrentUsers.values().stream().anyMatch(users -> users.get(0).getUsername().equals(username));
	}

}
