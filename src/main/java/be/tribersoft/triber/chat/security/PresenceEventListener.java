package be.tribersoft.triber.chat.security;

import java.util.Set;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.context.event.EventListener;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import be.tribersoft.triber.chat.user.domain.api.User;
import be.tribersoft.triber.chat.user.service.api.ConnectedUserListener;

@Named
public class PresenceEventListener {

	@Inject
	private Set<ConnectedUserListener> connectedUserListeners;

	@EventListener
	public void handleConnect(SessionConnectEvent event) {
		SecurityUserAuthentication securityUserAuthentication = (SecurityUserAuthentication) event.getUser();
		User user = securityUserAuthentication.getDetails().getUser();
		connectedUserListeners.stream().forEach(connectedUserListener -> connectedUserListener.userConnected(user));
	}

	@EventListener
	public void handleDisconnect(SessionDisconnectEvent event) {
		SecurityUserAuthentication securityUserAuthentication = (SecurityUserAuthentication) event.getUser();
		User user = securityUserAuthentication.getDetails().getUser();
		connectedUserListeners.stream().forEach(connectedUserListener -> connectedUserListener.userDisconnected(user));
	}
}
