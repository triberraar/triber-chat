package be.tribersoft.triber.chat.user.domain.impl;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import be.tribersoft.triber.chat.user.domain.api.NonUniqueEmailException;
import be.tribersoft.triber.chat.user.domain.api.NonUniqueUsernameException;
import be.tribersoft.triber.chat.user.domain.api.UserMessage;

@RunWith(MockitoJUnitRunner.class)
public class DefaultUserFacadeRegisterTest {

	private static final String EMAIL = "email";
	private static final String USERNAME = "username";
	@InjectMocks
	private DefaultUserFacade defaultUserFacade;
	@Mock
	private UserMessage userMessage;
	@Mock
	private DefaultUserRepository defaultUserRepository;
	@Mock
	private UserFactory userFactory;
	@Mock
	private UserEntity userEntity;

	@Before
	public void setUp() {
		when(userMessage.getEmail()).thenReturn(EMAIL);
		when(userMessage.getUsername()).thenReturn(USERNAME);
		when(userFactory.create(userMessage)).thenReturn(userEntity);
	}

	@Test(expected = NonUniqueUsernameException.class)
	public void failsWhenUsernameInUse() {
		when(defaultUserRepository.existsByUsername(USERNAME)).thenReturn(true);

		defaultUserFacade.register(userMessage);
	}

	@Test(expected = NonUniqueEmailException.class)
	public void failsWhenEmailInUse() {
		when(defaultUserRepository.existsByEmail(EMAIL)).thenReturn(true);

		defaultUserFacade.register(userMessage);
	}

	@Test
	public void savesNewUserOtherwise() {
		defaultUserFacade.register(userMessage);

		verify(defaultUserRepository).save(userEntity);
	}

}
