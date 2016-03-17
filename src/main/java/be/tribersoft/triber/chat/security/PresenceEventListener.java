package be.tribersoft.triber.chat.security;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.context.event.EventListener;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import be.tribersoft.triber.chat.common.WebSocketService;
import be.tribersoft.triber.chat.user.domain.api.ConnectedUsersRepository;
import be.tribersoft.triber.chat.user.domain.api.User;

@Named
public class PresenceEventListener {

	@Inject
	private ConnectedUsersRepository connectedUsersRepository;
	@Inject
	private WebSocketService webSocketService;

	@EventListener
	public void handleConnect(SessionConnectEvent event) {
		SecurityUserAuthentication securityUserAuthentication = (SecurityUserAuthentication) event.getUser();
		User user = securityUserAuthentication.getDetails().getUser();
		connectedUsersRepository.addUser(user);
		webSocketService.send("/topic/user/connected", user);
	}

	@EventListener
	public void handleDisconnect(SessionDisconnectEvent event) {
		SecurityUserAuthentication securityUserAuthentication = (SecurityUserAuthentication) event.getUser();
		User user = securityUserAuthentication.getDetails().getUser();
		connectedUsersRepository.removeUser(user);
		webSocketService.send("/topic/user/disconnected", user);
	}
}
