package be.tribersoft.triber.chat.user.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import be.tribersoft.triber.chat.user.domain.api.User;
import be.tribersoft.triber.chat.user.service.api.UserService;

@RunWith(MockitoJUnitRunner.class)
public class UserControllerAllTest {

	private static final String USERNAME = "username";
	private static final String ID = "id";
	private static final String EMAIL = "email";

	@InjectMocks
	private UserController userController;

	@Mock
	private UserService userService;

	private Map<String, String> searchParameters = new HashMap<>();
	@Mock
	private Pageable pageable;
	@Mock
	private Page<? extends User> page;
	@Mock
	private User user;

	@Before
	public void setUp() {
		doReturn(page).when(userService).findAll(pageable, searchParameters);
		doReturn(Arrays.asList(user)).when(page).getContent();
		when(user.getEmail()).thenReturn(EMAIL);
		when(user.getId()).thenReturn(ID);
		when(user.getUsername()).thenReturn(USERNAME);
		when(user.isActivated()).thenReturn(true);
		when(user.isValidated()).thenReturn(true);
	}

	@Test
	public void delegatesToService() {
		Page<UserToJsonAdapter> result = userController.all(pageable, searchParameters);

		assertThat(result.getContent()).hasSize(1);
		assertThat(result.getContent().get(0).getEmail()).isEqualTo(EMAIL);
		assertThat(result.getContent().get(0).getUsername()).isEqualTo(USERNAME);
		assertThat(result.getContent().get(0).getId()).isEqualTo(ID);
		assertThat(result.getContent().get(0).isActivated()).isTrue();
		assertThat(result.getContent().get(0).isValidated()).isTrue();
	}

}
