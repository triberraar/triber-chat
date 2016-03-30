package be.tribersoft.triber.chat.security;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import be.tribersoft.triber.chat.common.WebSocketService;
import be.tribersoft.triber.chat.user.domain.api.ConnectedUsersRepository;
import be.tribersoft.triber.chat.user.domain.api.User;

@RunWith(MockitoJUnitRunner.class)
public class PresenceEventListenerHandleDisconnectTest {

	@InjectMocks
	private PresenceEventListener presenceEventListener;

	@Mock
	private ConnectedUsersRepository connectedUsersRepository;
	@Mock
	private WebSocketService webSocketService;
	@Mock
	private SessionDisconnectEvent event;
	@Mock
	private SecurityUserAuthentication securityUserAuthentication;
	@Mock
	private SecurityUser securityUser;
	@Mock
	private User user;

	@Before
	public void setUp() {
		when(event.getUser()).thenReturn(securityUserAuthentication);
		when(securityUserAuthentication.getDetails()).thenReturn(securityUser);
		when(securityUser.getUser()).thenReturn(user);
	}

	@Test
	public void addsUserToCacheAndSendsWebsocketMessage() {
		presenceEventListener.handleDisconnect(event);

		verify(connectedUsersRepository).removeUser(user);
		verify(webSocketService).send("/topic/user/disconnected", user);
	}
}
