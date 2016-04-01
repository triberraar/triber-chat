package be.tribersoft.triber.chat.user.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import be.tribersoft.triber.chat.user.domain.api.ConnectedUsersRepository;
import be.tribersoft.triber.chat.user.domain.api.User;

@RunWith(MockitoJUnitRunner.class)
public class DefaultUserServiceFindAllConnectedTest {
	@InjectMocks
	private DefaultUserService defaultUserService;

	@Mock
	private ConnectedUsersRepository connectedUsersRepository;

	private User user;

	@Test
	public void delegatesToRepository() {
		doReturn(Arrays.asList(user)).when(connectedUsersRepository).findAll();

		List<? extends User> result = defaultUserService.findAllConnected();

		assertThat(result).isEqualTo(Arrays.asList(user));
	}
}
