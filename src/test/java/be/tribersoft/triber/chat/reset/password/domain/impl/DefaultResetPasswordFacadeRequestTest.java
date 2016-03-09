package be.tribersoft.triber.chat.reset.password.domain.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import be.tribersoft.triber.chat.reset.password.domain.api.ResetPasswordMessage;
import be.tribersoft.triber.chat.user.domain.api.User;
import be.tribersoft.triber.chat.user.domain.api.UserRepository;

@RunWith(MockitoJUnitRunner.class)
public class DefaultResetPasswordFacadeRequestTest {

	private static final String ID = "id";
	private static final String EMAIL = "email";

	@InjectMocks
	private DefaultResetPasswordFacade defaultResetPasswordFacade;

	@Mock
	private UserRepository userRepository;
	@Mock
	private ResetPasswordFactory resetPasswordFactory;
	@Mock
	private DefaultResetPasswordRepository defaultResetPasswordRepository;
	@Mock
	private ResetPasswordMessage resetPasswordMessage;
	@Mock
	private User user;
	private ResetPasswordEntity resetPasswordEntity;

	@Before
	public void setUp() {
		when(resetPasswordMessage.getEmail()).thenReturn(EMAIL);
		when(userRepository.getActivatedByEmail(EMAIL)).thenReturn(user);
		when(user.getId()).thenReturn(ID);
		when(resetPasswordFactory.create(ID)).thenReturn(resetPasswordEntity);
		when(defaultResetPasswordRepository.save(resetPasswordEntity)).thenReturn(resetPasswordEntity);
	}

	@Test
	public void requestsAResetPasswordForUser() {
		assertThat(defaultResetPasswordFacade.request(resetPasswordMessage)).isEqualTo(resetPasswordEntity);

	}

}
