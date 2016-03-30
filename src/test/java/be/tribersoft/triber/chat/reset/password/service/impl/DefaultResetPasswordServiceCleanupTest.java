package be.tribersoft.triber.chat.reset.password.service.impl;

import static org.mockito.Mockito.verify;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import be.tribersoft.triber.chat.reset.password.domain.api.ResetPasswordFacade;

@RunWith(MockitoJUnitRunner.class)
public class DefaultResetPasswordServiceCleanupTest {

	@InjectMocks
	private DefaultResetPasswordService defaultResetPasswordService;

	@Mock
	private ResetPasswordFacade resetPasswordFacade;

	@Test
	public void delegatesToFacade() {
		defaultResetPasswordService.cleanup();

		verify(resetPasswordFacade).cleanup();
	}
}
