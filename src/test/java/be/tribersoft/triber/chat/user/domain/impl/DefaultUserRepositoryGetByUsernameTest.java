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
public class DefaultUserRepositoryGetByUsernameTest {

	private static final String ID = "id";

	@InjectMocks
	private DefaultUserRepository defaultUserRepository;

	@Mock
	private UserJpaRepository userJpaRepository;
	@Mock
	private UserEntity user;

	@Test
	public void returnsWhenFound() {
		when(userJpaRepository.findById(ID)).thenReturn(Optional.of(user));

		UserEntity result = defaultUserRepository.getById(ID);

		assertThat(result).isEqualTo(user);
	}

	@Test(expected = UserNotFoundException.class)
	public void failsWhenNotFound() {
		when(userJpaRepository.findById(ID)).thenReturn(Optional.empty());

		defaultUserRepository.getById(ID);
	}
}
