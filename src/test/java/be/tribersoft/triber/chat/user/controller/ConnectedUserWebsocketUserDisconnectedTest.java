package be.tribersoft.triber.chat.user.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import be.tribersoft.triber.chat.common.WebSocketService;
import be.tribersoft.triber.chat.user.domain.api.User;

@RunWith(MockitoJUnitRunner.class)
public class ConnectedUserWebsocketUserDisconnectedTest {

	private static final String NAME = "name";

	@InjectMocks
	private ConnectedUserWebsocket connectedUserWebsocket;

	@Mock
	private WebSocketService webSocketService;

	@Mock
	private User user;

	@Captor
	private ArgumentCaptor<UserToJsonAdapter> userToJsonAdapterCaptor;

	@Before
	public void setUp() {
		when(user.getUsername()).thenReturn(NAME);
	}

	@Test
	public void shouldBroadcast() {
		connectedUserWebsocket.userDisconnected(user);

		verify(webSocketService).send(eq("/topic/user/disconnected"), userToJsonAdapterCaptor.capture());
		assertThat(userToJsonAdapterCaptor.getValue().getUsername()).isEqualTo(NAME);
	}
}
