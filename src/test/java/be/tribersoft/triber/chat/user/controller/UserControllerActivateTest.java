package be.tribersoft.triber.chat.user.controller;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import be.tribersoft.triber.chat.register.controller.ActivateRegistrationFromJsonAdapter;
import be.tribersoft.triber.chat.user.service.api.UserService;

@RunWith(MockitoJUnitRunner.class)
public class UserControllerActivateTest {

	private static final String PASSWORD = "password";
	private static final String ID = "id";

	@InjectMocks
	private UserController userController;

	@Mock
	private UserService userService;
	@Mock
	private ActivateRegistrationFromJsonAdapter json;

	@Test
	public void delegatesToService() {
		when(json.getPassword()).thenReturn(PASSWORD);

		userController.activate(json, ID);

		verify(userService).activate(ID, PASSWORD);
	}

}
