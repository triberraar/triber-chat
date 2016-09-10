package be.tribersoft.triber.chat.user.service.api;

import be.tribersoft.triber.chat.user.domain.api.User;

public interface ConnectedUserListener {

	void userConnected(User user);

	void userDisconnected(User user);
}
