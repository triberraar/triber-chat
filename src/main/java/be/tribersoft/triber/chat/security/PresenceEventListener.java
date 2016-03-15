package be.tribersoft.triber.chat.security;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.context.event.EventListener;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import be.tribersoft.triber.chat.user.domain.api.ConnectedUsersRepository;

@Named
public class PresenceEventListener {

	@Inject
	private ConnectedUsersRepository connectedUsersRepository;

	@EventListener
	public void handleConnect(SessionConnectedEvent event) {
		SecurityUserAuthentication securityUserAuthentication = (SecurityUserAuthentication) event.getUser();
		connectedUsersRepository.addUser(securityUserAuthentication.getDetails().getUser());
	}

	@EventListener
	public void handleDisconnect(SessionDisconnectEvent event) {
		SecurityUserAuthentication securityUserAuthentication = (SecurityUserAuthentication) event.getUser();
		connectedUsersRepository.removeUser(securityUserAuthentication.getDetails().getUser());
	}
}
