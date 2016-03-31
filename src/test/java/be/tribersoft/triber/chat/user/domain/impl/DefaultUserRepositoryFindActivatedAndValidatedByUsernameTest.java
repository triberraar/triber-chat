package be.tribersoft.triber.chat.user.domain.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import be.tribersoft.triber.chat.user.domain.api.User;

@RunWith(MockitoJUnitRunner.class)
public class DefaultUserRepositoryFindActivatedAndValidatedByUsernameTest {

	private static final String USERNAME = "username";

	@InjectMocks
	private DefaultUserRepository defaultUserRepository;

	@Mock
	private UserJpaRepository userJpaRepository;

	@Mock
	private UserEntity user;

	@Test
	public void returnsUserWhenFound() {
		when(userJpaRepository.findByUsernameAndActivatedAndValidated(USERNAME, true, true)).thenReturn(Optional.of(user));

		Optional<User> result = defaultUserRepository.findActivatedAndValidatedByUsername(USERNAME);

		assertThat(result).contains(user);

	}

	@Test
	public void returnsAbsentWhenNotFound() {
		when(userJpaRepository.findByUsernameAndActivatedAndValidated(USERNAME, true, true)).thenReturn(Optional.empty());

		Optional<User> result = defaultUserRepository.findActivatedAndValidatedByUsername(USERNAME);

		assertThat(result).isEmpty();

	}
}
