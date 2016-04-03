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
public class DefaultUserRepositoryGetNotValidatedByIdTest {

	private static final String ID = "id";
	@InjectMocks
	private DefaultUserRepository defaultUserRepository;
	@Mock
	private UserJpaRepository userJpaRepository;
	@Mock
	private UserEntity userEntity;

	@Test(expected = UserNotFoundException.class)
	public void failsWhenUserNotFound() {
		when(userJpaRepository.findByIdAndValidated(ID, false)).thenReturn(Optional.empty());

		defaultUserRepository.getNotValidatedById(ID);
	}

	@Test
	public void returnsUserWhenFound() {
		when(userJpaRepository.findByIdAndValidated(ID, false)).thenReturn(Optional.of(userEntity));

		assertThat(defaultUserRepository.getNotValidatedById(ID)).isEqualTo(userEntity);

	}
}
