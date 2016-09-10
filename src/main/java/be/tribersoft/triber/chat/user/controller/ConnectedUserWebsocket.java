package be.tribersoft.triber.chat.user.controller;

import javax.inject.Inject;
import javax.inject.Named;

import be.tribersoft.triber.chat.common.WebSocketService;
import be.tribersoft.triber.chat.user.domain.api.User;
import be.tribersoft.triber.chat.user.service.api.ConnectedUserListener;

@Named
public class ConnectedUserWebsocket implements ConnectedUserListener {

	@Inject
	private WebSocketService webSocketService;

	@Override
	public void userConnected(User user) {
		webSocketService.send("/topic/user/connected", new UserToJsonAdapter(user));
	}

	@Override
	public void userDisconnected(User user) {
		webSocketService.send("/topic/user/disconnected", new UserToJsonAdapter(user));
	}

}
