package be.tribersoft.triber.chat.user.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import be.tribersoft.triber.chat.user.domain.api.UserRepository;

@RunWith(MockitoJUnitRunner.class)
public class DefaultUserServiceExistsUnvalidatedTest {

	@InjectMocks
	private DefaultUserService defaultUserService;

	@Mock
	private UserRepository userRepository;

	@Test
	public void delegatesToRepository() {
		when(userRepository.existsUnvalidated()).thenReturn(true);

		boolean result = defaultUserService.existsUnvalidated();

		assertThat(result).isTrue();
	}
}
