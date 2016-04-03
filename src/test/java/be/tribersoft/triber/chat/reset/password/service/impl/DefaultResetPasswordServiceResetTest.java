package be.tribersoft.triber.chat.reset.password.service.impl;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import be.tribersoft.triber.chat.reset.password.domain.api.ResetPassword;
import be.tribersoft.triber.chat.reset.password.domain.api.ResetPasswordFacade;
import be.tribersoft.triber.chat.reset.password.domain.api.ResetPasswordMessage;
import be.tribersoft.triber.chat.user.domain.api.User;
import be.tribersoft.triber.chat.user.domain.api.UserRepository;

@RunWith(MockitoJUnitRunner.class)
public class DefaultResetPasswordServiceResetTest {

	private static final String USERNAME = "username";
	private static final String ID = "id";
	private static final String EMAIL = "email";

	@InjectMocks
	private DefaultResetPasswordService defaultResetPasswordService;

	@Mock
	private ResetPasswordFacade resetPasswordFacade;
	@Mock
	private ResetPasswordMailService resetPasswordMailService;
	@Mock
	private UserRepository userRepository;
	@Mock
	private ResetPasswordMessage resetPasswordMessage;
	@Mock
	private ResetPassword resetPassword;
	@Mock
	private User user;

	@Before
	public void setUp() {
		when(resetPasswordMessage.getEmail()).thenReturn(EMAIL);
		when(resetPasswordFacade.request(resetPasswordMessage)).thenReturn(resetPassword);
		when(userRepository.getActivatedByEmail(EMAIL)).thenReturn(user);
		when(resetPassword.getId()).thenReturn(ID);
		when(user.getUsername()).thenReturn(USERNAME);
	}

	@Test
	public void createsAResetPasswordAndSendsAMail() {
		defaultResetPasswordService.request(resetPasswordMessage);

		verify(resetPasswordMailService).sendMail(ID, USERNAME, EMAIL);
	}
}
