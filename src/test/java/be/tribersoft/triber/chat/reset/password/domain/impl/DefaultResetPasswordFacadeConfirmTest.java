package be.tribersoft.triber.chat.reset.password.domain.impl;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import be.tribersoft.triber.chat.reset.password.domain.api.ResetPasswordConfirmationMessage;
import be.tribersoft.triber.chat.user.domain.api.UserFacade;

@RunWith(MockitoJUnitRunner.class)
public class DefaultResetPasswordFacadeConfirmTest {

	private static final String PASSWORD = "password";
	private static final String USER_ID = "userId";
	private static final String RESET_PASSWORD_ID = "reset password id";

	@InjectMocks
	private DefaultResetPasswordFacade defaultResetPasswordFacade;

	@Mock
	private DefaultResetPasswordRepository defaultResetPasswordRepository;
	@Mock
	private UserFacade userFacade;
	@Mock
	private ResetPasswordConfirmationMessage resetPasswordConfirmationMessage;
	@Mock
	private ResetPasswordEntity resetPasswordEntity;

	@Before
	public void setUp() {
		when(defaultResetPasswordRepository.getById(RESET_PASSWORD_ID)).thenReturn(resetPasswordEntity);
		when(resetPasswordEntity.getUserId()).thenReturn(USER_ID);
		when(resetPasswordConfirmationMessage.getPassword()).thenReturn(PASSWORD);

	}

	@Test
	public void changesPasswordAndDeletesResetPassword() {
		defaultResetPasswordFacade.confirm(RESET_PASSWORD_ID, resetPasswordConfirmationMessage);

		verify(userFacade).changePassword(USER_ID, PASSWORD);
		verify(defaultResetPasswordRepository).delete(resetPasswordEntity);
	}

}
