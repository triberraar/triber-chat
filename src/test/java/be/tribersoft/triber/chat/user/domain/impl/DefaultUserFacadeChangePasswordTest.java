package be.tribersoft.triber.chat.user.domain.impl;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class DefaultUserFacadeChangePasswordTest {

	private static final String ID = "id";
	private static final String PASSWORD = "password";

	@InjectMocks
	private DefaultUserFacade defaultUserFacade;

	@Mock
	private DefaultUserRepository defaultUserRepository;
	@Mock
	private UserEntity user;

	@Before
	public void setUp() {
		when(defaultUserRepository.getActivatedById(ID)).thenReturn(user);
	}

	@Test
	public void changesPassword() {
		defaultUserFacade.changePassword(ID, PASSWORD);

		verify(user).changePassword(PASSWORD);
	}

}
