package be.tribersoft.triber.chat.user.domain.api;

import java.util.Collection;

public interface ConnectedUsersRepository {

	void addUser(User user);

	void removeUser(User user);

	Collection<User> findAll();

}
