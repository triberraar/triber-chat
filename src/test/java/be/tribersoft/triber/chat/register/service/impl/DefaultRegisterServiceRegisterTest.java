package be.tribersoft.triber.chat.register.service.impl;

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
import be.tribersoft.triber.chat.user.domain.api.UserMessage;

@RunWith(MockitoJUnitRunner.class)
public class DefaultRegisterServiceRegisterTest {

	private static final String EMAIL = "email";
	private static final String ID = "id";
	private static final String USERNAME = "username";

	@InjectMocks
	private DefaultRegisterService defaultRegisterService;

	@Mock
	private UserFacade userFacade;
	@Mock
	private RegisterMailService registerMailService;
	@Mock
	private UserMessage userMessage;
	@Mock
	private User user;
	@Mock
	private WebSocketService webSocketService;

	@Before
	public void setUp() {
		when(userFacade.register(userMessage)).thenReturn(user);
		when(user.getUsername()).thenReturn(USERNAME);
		when(user.getId()).thenReturn(ID);
		when(user.getEmail()).thenReturn(EMAIL);
	}

	@Test
	public void registersUserAndSendEMailAndBroadcastsRegistration() {
		defaultRegisterService.register(userMessage);

		verify(userFacade).register(userMessage);
		verify(registerMailService).sendMail(USERNAME, ID, EMAIL);
		verify(webSocketService).send("/topic/notifications/registeredUser", user);
	}
}
