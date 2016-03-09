package be.tribersoft.triber.chat.reset.password.domain.impl;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class DefaultResetPasswordFacadeCleanupTest {

	@InjectMocks
	private DefaultResetPasswordFacade defaultResetPasswordFacade;
	@Mock
	private DefaultResetPasswordRepository defaultResetPasswordRepository;
	@Mock
	private ResetPasswordEntity resetPasswordEntity;

	@Test
	public void removesExpiredResetPasswords() {
		when(defaultResetPasswordRepository.findExpired()).thenReturn(Arrays.asList(resetPasswordEntity));

		defaultResetPasswordFacade.cleanup();

		verify(defaultResetPasswordRepository).delete(Arrays.asList(resetPasswordEntity));
	}

}
