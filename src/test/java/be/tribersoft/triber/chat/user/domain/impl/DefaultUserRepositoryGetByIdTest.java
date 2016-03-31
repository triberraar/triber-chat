package be.tribersoft.triber.chat.user.domain.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import be.tribersoft.triber.chat.user.domain.api.UserNotFoundException;

@RunWith(MockitoJUnitRunner.class)
public class DefaultUserRepositoryGetByIdTest {

	private static final String USERNAME = "username";

	@InjectMocks
	private DefaultUserRepository defaultUserRepository;

	@Mock
	private UserJpaRepository userJpaRepository;
	@Mock
	private UserEntity user;

	@Test
	public void returnsWhenFound() {
		when(userJpaRepository.findByUsername(USERNAME)).thenReturn(Optional.of(user));

		UserEntity result = defaultUserRepository.getByUsername(USERNAME);

		assertThat(result).isEqualTo(user);
	}

	@Test(expected = UserNotFoundException.class)
	public void failsWhenNotFound() {
		when(userJpaRepository.findByUsername(USERNAME)).thenReturn(Optional.empty());

		defaultUserRepository.getByUsername(USERNAME);
	}
}
