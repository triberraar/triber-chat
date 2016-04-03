package be.tribersoft.triber.chat.user.controller;

import static org.mockito.Mockito.verify;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import be.tribersoft.triber.chat.user.service.api.UserService;

@RunWith(MockitoJUnitRunner.class)
public class UserControllerValidateTest {

	private static final String ID = "id";

	@InjectMocks
	private UserController userController;

	@Mock
	private UserService userService;

	@Test
	public void delegatesToService() {
		userController.validate(ID);

		verify(userService).validate(ID);
	}

}
