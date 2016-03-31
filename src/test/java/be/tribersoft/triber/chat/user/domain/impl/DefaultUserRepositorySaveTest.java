package be.tribersoft.triber.chat.user.domain.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class DefaultUserRepositorySaveTest {

	private static final String EMAIL = "email";

	@InjectMocks
	private DefaultUserRepository defaultUserRepository;

	@Mock
	private UserJpaRepository userJpaRepository;

	@Mock
	private UserEntity user;

	@Before
	public void setUp() {
		when(userJpaRepository.findByEmail(EMAIL)).thenReturn(Optional.of(user));
	}

	@Test
	public void delegatesToJpaRepository() {
		assertThat(defaultUserRepository.existsByEmail(EMAIL)).isTrue();
	}
}
