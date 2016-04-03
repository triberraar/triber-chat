package be.tribersoft.triber.chat.user.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import be.tribersoft.triber.chat.user.domain.api.User;
import be.tribersoft.triber.chat.user.service.api.UserService;

@RunWith(MockitoJUnitRunner.class)
public class UserControllerAllConnectedTest {
	private static final String USERNAME = "username";
	private static final String ID = "id";
	private static final String EMAIL = "email";

	@InjectMocks
	private UserController userController;

	@Mock
	private UserService userService;

	@Mock
	private User user;

	@Before
	public void setUp() {
		doReturn(Arrays.asList(user)).when(userService).findAllConnected();
		when(user.getEmail()).thenReturn(EMAIL);
		when(user.getId()).thenReturn(ID);
		when(user.getUsername()).thenReturn(USERNAME);
		when(user.isActivated()).thenReturn(true);
		when(user.isValidated()).thenReturn(true);
	}

	@Test
	public void delegatesToService() {
		List<UserToJsonAdapter> result = userController.allConnected();

		assertThat(result).hasSize(1);
		assertThat(result.get(0).getEmail()).isEqualTo(EMAIL);
		assertThat(result.get(0).getUsername()).isEqualTo(USERNAME);
		assertThat(result.get(0).getId()).isEqualTo(ID);
		assertThat(result.get(0).isActivated()).isTrue();
		assertThat(result.get(0).isValidated()).isTrue();
	}
}
