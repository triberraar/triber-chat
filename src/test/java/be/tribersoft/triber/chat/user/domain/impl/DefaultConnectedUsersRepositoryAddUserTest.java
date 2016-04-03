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
public class DefaultConnectedUsersRepositoryAddUserTest {

	private static final String ID = "id";

	private DefaultConnectedUsersRepository defaultConnectedUserRepository = new DefaultConnectedUsersRepository();

	@Mock
	private User user;

	@Before
	public void setUp() {
		when(user.getId()).thenReturn(ID);
	}

	@Test
	public void addsUserWhenUSerNotConnectedBefore() {
		assertThat(defaultConnectedUserRepository.findAll()).isEmpty();

		defaultConnectedUserRepository.addUser(user);

		assertThat(defaultConnectedUserRepository.findAll()).hasSize(1).contains(user);
	}

	@Test
	public void addsUserWhenUserConnectedBefore() {
		assertThat(defaultConnectedUserRepository.findAll()).isEmpty();

		defaultConnectedUserRepository.addUser(user);

		assertThat(defaultConnectedUserRepository.findAll()).hasSize(1).contains(user);

		defaultConnectedUserRepository.addUser(user);

		assertThat(defaultConnectedUserRepository.findAll()).hasSize(1).contains(user);

	}

}
