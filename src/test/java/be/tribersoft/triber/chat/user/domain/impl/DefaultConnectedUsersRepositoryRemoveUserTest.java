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
public class DefaultConnectedUsersRepositoryRemoveUserTest {

	private static final String ID = "id";

	private DefaultConnectedUsersRepository defaultConnectedUserRepository = new DefaultConnectedUsersRepository();

	@Mock
	private User user;

	@Before
	public void setUp() {
		when(user.getId()).thenReturn(ID);
	}

	@Test
	public void removesUserWhenUserOnlyConnectedOnce() {
		defaultConnectedUserRepository.addUser(user);

		assertThat(defaultConnectedUserRepository.findAll()).hasSize(1).contains(user);

		defaultConnectedUserRepository.removeUser(user);

		assertThat(defaultConnectedUserRepository.findAll()).isEmpty();
	}

	@Test
	public void doestremovsUserWhenUserOnlyConnectedMoreThanOnce() {
		defaultConnectedUserRepository.addUser(user);
		defaultConnectedUserRepository.addUser(user);

		defaultConnectedUserRepository.removeUser(user);

		assertThat(defaultConnectedUserRepository.findAll()).hasSize(1).contains(user);
	}

}
