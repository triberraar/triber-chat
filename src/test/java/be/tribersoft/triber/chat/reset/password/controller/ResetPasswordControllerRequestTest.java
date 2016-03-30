package be.tribersoft.triber.chat.reset.password.controller;

import static org.mockito.Mockito.verify;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import be.tribersoft.triber.chat.reset.password.service.api.ResetPasswordService;

@RunWith(MockitoJUnitRunner.class)
public class ResetPasswordControllerRequestTest {

	@InjectMocks
	private ResetPasswordController resetPasswordController;

	@Mock
	private ResetPasswordService resetPasswordService;

	private ResetPasswordRequestFromJsonAdapter json;

	@Test
	public void delegatesToService() {
		resetPasswordController.request(json);

		verify(resetPasswordService).request(json);
	}

}
