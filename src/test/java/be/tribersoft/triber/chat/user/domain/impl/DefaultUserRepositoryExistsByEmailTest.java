package be.tribersoft.triber.chat.user.domain.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class DefaultUserRepositoryExistsByEmailTest {

	@InjectMocks
	private DefaultUserRepository defaultUserRepository;

	@Mock
	private UserJpaRepository userJpaRepository;

	@Mock
	private UserEntity user;

	@Before
	public void setUp() {
		when(userJpaRepository.save(user)).thenReturn(user);
	}

	@Test
	public void delegatesToJpaRepository() {
		assertThat(defaultUserRepository.save(user)).isEqualTo(user);
	}
}
