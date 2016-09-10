package be.tribersoft.triber.chat.user.domain.impl;

import javax.inject.Inject;
import javax.inject.Named;

import be.tribersoft.triber.chat.user.domain.api.ConnectedUsersRepository;
import be.tribersoft.triber.chat.user.domain.api.User;
import be.tribersoft.triber.chat.user.service.api.ConnectedUserListener;

@Named
public class DefaultConnectedUserListener implements ConnectedUserListener {

	@Inject
	private ConnectedUsersRepository connectedUsersRepository;

	@Override
	public void userConnected(User user) {
		connectedUsersRepository.addUser(user);
	}

	@Override
	public void userDisconnected(User user) {
		connectedUsersRepository.removeUser(user);
	}

}
