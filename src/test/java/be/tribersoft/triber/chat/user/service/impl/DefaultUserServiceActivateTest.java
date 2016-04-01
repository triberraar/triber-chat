package be.tribersoft.triber.chat.user.service.impl;

import static org.mockito.Mockito.verify;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import be.tribersoft.triber.chat.user.domain.api.UserFacade;

@RunWith(MockitoJUnitRunner.class)
public class DefaultUserServiceActivateTest {

	private static final String PASSWORD = "password";
	private static final String ID = "id";

	@InjectMocks
	private DefaultUserService defaultUserService;

	@Mock
	private UserFacade userFacade;

	@Test
	public void delegatesToFacade() {
		defaultUserService.activate(ID, PASSWORD);

		verify(userFacade).activate(ID, PASSWORD);
	}
}
