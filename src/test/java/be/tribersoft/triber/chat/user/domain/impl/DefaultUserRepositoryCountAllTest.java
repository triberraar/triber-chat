package be.tribersoft.triber.chat.user.domain.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class DefaultUserRepositoryCountAllTest {

	private static final Long COUNT = 23L;

	@InjectMocks
	private DefaultUserRepository defaultUserRepository;

	@Mock
	private UserJpaRepository userJpaRepository;

	@Test
	public void delegatesToJpaRepository() {
		when(userJpaRepository.count()).thenReturn(COUNT);

		assertThat(defaultUserRepository.countAll()).isEqualTo(COUNT);
	}
}
