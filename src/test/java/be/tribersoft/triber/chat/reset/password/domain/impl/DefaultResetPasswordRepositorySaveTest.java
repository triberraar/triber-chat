package be.tribersoft.triber.chat.reset.password.domain.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class DefaultResetPasswordRepositorySaveTest {

	@InjectMocks
	private DefaultResetPasswordRepository defaultResetPasswordRepository;
	@Mock
	private ResetPasswordJpaRepository resetPasswordJpaRepository;
	@Mock
	private ResetPasswordEntity resetPasswordEntity;

	@Test
	public void delegatesToJpaRepository() {
		when(resetPasswordJpaRepository.save(resetPasswordEntity)).thenReturn(resetPasswordEntity);

		assertThat(defaultResetPasswordRepository.save(resetPasswordEntity)).isEqualTo(resetPasswordEntity);
	}

}
