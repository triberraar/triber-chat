package be.tribersoft.triber.chat.security;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.web.socket.messaging.SessionConnectEvent;

import be.tribersoft.triber.chat.common.WebSocketService;
import be.tribersoft.triber.chat.user.domain.api.ConnectedUsersRepository;
import be.tribersoft.triber.chat.user.domain.api.User;

@RunWith(MockitoJUnitRunner.class)
public class PresenceEventListenerHandleConnectTest {

	@InjectMocks
	private PresenceEventListener presenceEventListener;

	@Mock
	private ConnectedUsersRepository connectedUsersRepository;
	@Mock
	private WebSocketService webSocketService;
	@Mock
	private SessionConnectEvent event;
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
		presenceEventListener.handleConnect(event);

		verify(connectedUsersRepository).addUser(user);
		verify(webSocketService).send("/topic/user/connected", user);
	}
}
