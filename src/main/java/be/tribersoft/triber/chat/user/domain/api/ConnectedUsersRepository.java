package be.tribersoft.triber.chat.user.domain.api;

import java.util.List;

public interface ConnectedUsersRepository {

	void addUser(User user);

	void removeUser(User user);

	List<User> findAll();

	boolean exists(String username);

}
