package be.tribersoft.triber.chat.security;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.HashSet;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.internal.util.reflection.Whitebox;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.web.socket.messaging.SessionConnectEvent;

import be.tribersoft.triber.chat.user.domain.api.User;
import be.tribersoft.triber.chat.user.service.api.ConnectedUserListener;

@RunWith(MockitoJUnitRunner.class)
public class PresenceEventListenerHandleConnectTest {

	@InjectMocks
	private PresenceEventListener presenceEventListener;

	@Mock
	private SessionConnectEvent event;
	@Mock
	private SecurityUserAuthentication securityUserAuthentication;
	@Mock
	private SecurityUser securityUser;
	@Mock
	private User user;
	@Mock
	private ConnectedUserListener connectedUserListener1, connectedUserListener2;

	@Before
	public void setUp() {
		when(event.getUser()).thenReturn(securityUserAuthentication);
		when(securityUserAuthentication.getDetails()).thenReturn(securityUser);
		when(securityUser.getUser()).thenReturn(user);
		Whitebox.setInternalState(presenceEventListener, "connectedUserListeners", new HashSet<>(Arrays.asList(connectedUserListener1, connectedUserListener2)));
	}

	@Test
	public void shouldNotifyAllListeners() {
		presenceEventListener.handleConnect(event);

		verify(connectedUserListener1).userConnected(user);
		verify(connectedUserListener2).userConnected(user);
	}
}
