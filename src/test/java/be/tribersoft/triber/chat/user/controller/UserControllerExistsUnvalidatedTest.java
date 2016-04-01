package be.tribersoft.triber.chat.user.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import be.tribersoft.triber.chat.user.service.api.UserService;

@RunWith(MockitoJUnitRunner.class)
public class UserControllerExistsUnvalidatedTest {

	@InjectMocks
	private UserController userController;

	@Mock
	private UserService userService;

	@Test
	public void returnsOKWhenUnvalidatedUsersExist() {
		when(userService.existsUnvalidated()).thenReturn(true);

		ResponseEntity<?> result = userController.existsUnvalidated();

		assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
	}

	@Test
	public void returnsNotFoundWhenNoUnvalidatedUsersExist() {
		when(userService.existsUnvalidated()).thenReturn(false);

		ResponseEntity<?> result = userController.existsUnvalidated();

		assertThat(result.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);

	}
}
