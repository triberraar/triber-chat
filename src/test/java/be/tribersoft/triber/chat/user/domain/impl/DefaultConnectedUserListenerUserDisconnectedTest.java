package be.tribersoft.triber.chat.user.domain.impl;

import static org.mockito.Mockito.verify;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import be.tribersoft.triber.chat.user.domain.api.ConnectedUsersRepository;
import be.tribersoft.triber.chat.user.domain.api.User;

@RunWith(MockitoJUnitRunner.class)
public class DefaultConnectedUserListenerUserDisconnectedTest {

	@InjectMocks
	private DefaultConnectedUserListener defaultConnectedUserListener;

	@Mock
	private ConnectedUsersRepository connectedUsersRepository;

	@Mock
	private User user;

	@Test
	public void removesUserFromConnectedUsersRepository() {
		defaultConnectedUserListener.userDisconnected(user);

		verify(connectedUsersRepository).removeUser(user);
	}
}
