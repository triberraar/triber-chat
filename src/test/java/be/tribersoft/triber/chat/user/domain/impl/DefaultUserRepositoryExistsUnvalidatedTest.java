package be.tribersoft.triber.chat.user.domain.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class DefaultUserRepositoryExistsUnvalidatedTest {

	@InjectMocks
	private DefaultUserRepository defaultUserRepository;

	@Mock
	private UserJpaRepository userJpaRepository;

	@Test
	public void returnsTrueWhenUnvalidatedExists() {
		when(userJpaRepository.countByValidated(false)).thenReturn(1L);

		assertThat(defaultUserRepository.existsUnvalidated()).isTrue();
	}

	@Test
	public void returnsFalseWhenUnvalidatedNotExists() {
		when(userJpaRepository.countByValidated(false)).thenReturn(0L);

		assertThat(defaultUserRepository.existsUnvalidated()).isFalse();
	}
}
