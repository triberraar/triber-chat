package be.tribersoft.triber.chat.user.service.impl;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import be.tribersoft.triber.chat.common.WebSocketService;
import be.tribersoft.triber.chat.user.domain.api.User;
import be.tribersoft.triber.chat.user.domain.api.UserFacade;
import be.tribersoft.triber.chat.user.domain.api.UserRepository;

@RunWith(MockitoJUnitRunner.class)
public class DefaultUserServiceValidateTest {

	private static final String EMAIL = "mail@mail";
	private static final String USERNAME = "username";
	private static final String ID = "Id";

	@InjectMocks
	private DefaultUserService defaultUserService;

	@Mock
	private UserFacade userFacade;
	@Mock
	private ValidatedUserMailService validatedUserMailService;
	@Mock
	private UserRepository userRepository;
	@Mock
	private WebSocketService webSocketService;
	@Mock
	private User user;

	@Before
	public void setUp() {
		when(userRepository.getById(ID)).thenReturn(user);
		when(user.getUsername()).thenReturn(USERNAME);
		when(user.getEmail()).thenReturn(EMAIL);
	}

	@Test
	public void validatesUserSendsMailAndBroadcastsOverWebsocket() {
		defaultUserService.validate(ID);

		verify(userFacade).validate(ID);
		verify(validatedUserMailService).sendMail(USERNAME, EMAIL);
		verify(webSocketService).send("/topic/notifications/validatedUser", user);
	}

}
