package be.tribersoft.triber.chat.user.domain.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import be.tribersoft.triber.chat.user.domain.api.User;

@RunWith(MockitoJUnitRunner.class)
public class DefaultConnectedUsersRepositoryRemoveFindAllTest {

	private static final String ID_1 = "id1";
	private static final String ID_2 = "id2";

	private DefaultConnectedUsersRepository defaultConnectedUserRepository = new DefaultConnectedUsersRepository();

	@Mock
	private User user1, user2;

	@Before
	public void setUp() {
		when(user1.getId()).thenReturn(ID_1);
		when(user1.getUsernameLowerCase()).thenReturn("username b");
		when(user2.getId()).thenReturn(ID_2);
		when(user2.getUsernameLowerCase()).thenReturn("username a");
	}

	@Test
	public void returnsUserSortedOnUsername() {
		defaultConnectedUserRepository.addUser(user1);
		defaultConnectedUserRepository.addUser(user2);

		assertThat(defaultConnectedUserRepository.findAll()).hasSize(2).isEqualTo(Arrays.asList(user2, user1));
	}

}
