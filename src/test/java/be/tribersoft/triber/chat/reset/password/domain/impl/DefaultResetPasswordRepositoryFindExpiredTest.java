package be.tribersoft.triber.chat.reset.password.domain.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import be.tribersoft.triber.chat.common.DateFactory;

@RunWith(MockitoJUnitRunner.class)
public class DefaultResetPasswordRepositoryFindExpiredTest {
	private static final Date NOW = new Date();

	@InjectMocks
	private DefaultResetPasswordRepository defaultResetPasswordRepository;

	@Mock
	private ResetPasswordJpaRepository resetPasswordJpaRepository;
	@Mock
	private ResetPasswordEntity resetPasswordEntity;

	@Test
	public void delegatesToJpaRepository() {
		DateFactory.fixate(NOW);
		when(resetPasswordJpaRepository.findByExpireDateBefore(NOW)).thenReturn(Arrays.asList(resetPasswordEntity));

		List<ResetPasswordEntity> result = defaultResetPasswordRepository.findExpired();

		assertThat(result).isEqualTo(Arrays.asList(resetPasswordEntity));

	}
}
