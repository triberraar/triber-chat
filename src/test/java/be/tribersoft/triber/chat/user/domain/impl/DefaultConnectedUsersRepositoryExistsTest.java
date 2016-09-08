package be.tribersoft.triber.chat.user.domain.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import be.tribersoft.triber.chat.user.domain.api.User;

@RunWith(MockitoJUnitRunner.class)
public class DefaultConnectedUsersRepositoryExistsTest {

	private static final String ID = "id";
	private static final String CONNECTED_USER_USERNAME = "connected";
	private static final String UNCONNECTED_USER_USERNAME = "unconnected";

	private DefaultConnectedUsersRepository defaultConnectedUserRepository = new DefaultConnectedUsersRepository();

	@Mock
	private User connectedUser;

	@Before
	public void setUp() {
		when(connectedUser.getId()).thenReturn(ID);
		when(connectedUser.getUsername()).thenReturn(CONNECTED_USER_USERNAME);
		defaultConnectedUserRepository.addUser(connectedUser);
	}

	@Test
	public void returnsTrueIfUserIsConnected() {
		assertThat(defaultConnectedUserRepository.exists(CONNECTED_USER_USERNAME)).isTrue();
	}

	@Test
	public void returnsFalseIfUserIsNotConnected() {
		assertThat(defaultConnectedUserRepository.exists(UNCONNECTED_USER_USERNAME)).isFalse();
	}

}
