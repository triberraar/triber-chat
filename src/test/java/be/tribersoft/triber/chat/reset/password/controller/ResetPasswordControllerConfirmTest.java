package be.tribersoft.triber.chat.reset.password.controller;

import static org.mockito.Mockito.verify;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import be.tribersoft.triber.chat.reset.password.service.api.ResetPasswordService;

@RunWith(MockitoJUnitRunner.class)
public class ResetPasswordControllerConfirmTest {
	private static final String ID = "id";

	@InjectMocks
	private ResetPasswordController resetPasswordController;

	@Mock
	private ResetPasswordService resetPasswordService;

	private ResetPasswordConfirmationFromJsonAdapter json;

	@Test
	public void delegatesToService() {
		resetPasswordController.confirm(ID, json);

		verify(resetPasswordService).confirm(ID, json);
	}
}
