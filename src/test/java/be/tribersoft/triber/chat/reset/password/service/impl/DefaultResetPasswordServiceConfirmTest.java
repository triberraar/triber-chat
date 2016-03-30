package be.tribersoft.triber.chat.reset.password.service.impl;

import static org.mockito.Mockito.verify;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import be.tribersoft.triber.chat.reset.password.domain.api.ResetPasswordConfirmationMessage;
import be.tribersoft.triber.chat.reset.password.domain.api.ResetPasswordFacade;

@RunWith(MockitoJUnitRunner.class)
public class DefaultResetPasswordServiceConfirmTest {

	private static final String ID = "id";

	@InjectMocks
	private DefaultResetPasswordService defaultResetPasswordService;

	@Mock
	private ResetPasswordFacade resetPasswordFacade;

	@Mock
	private ResetPasswordConfirmationMessage resetPasswordConfirmationMessage;

	@Test
	public void delegatesToFacade() {
		defaultResetPasswordService.confirm(ID, resetPasswordConfirmationMessage);

		verify(resetPasswordFacade).confirm(ID, resetPasswordConfirmationMessage);
	}
}
