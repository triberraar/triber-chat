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
public class DefaultUserRepositoryGetActivatedByEmailTest {

	private static final String EMAIL = "email";
	@InjectMocks
	private DefaultUserRepository defaultUserRepository;
	@Mock
	private UserJpaRepository userJpaRepository;
	@Mock
	private UserEntity userEntity;

	@Test(expected = UserNotFoundException.class)
	public void failsWhenNotFound() {
		when(userJpaRepository.findByEmailAndActivated(EMAIL, true)).thenReturn(Optional.<UserEntity> empty());

		defaultUserRepository.getActivatedByEmail(EMAIL);
	}

	@Test
	public void returnsWhenFound() {
		when(userJpaRepository.findByEmailAndActivated(EMAIL, true)).thenReturn(Optional.of(userEntity));

		assertThat(defaultUserRepository.getActivatedByEmail(EMAIL)).isEqualTo(userEntity);

	}
}
