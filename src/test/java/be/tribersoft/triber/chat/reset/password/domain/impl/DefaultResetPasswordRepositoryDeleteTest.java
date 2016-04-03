package be.tribersoft.triber.chat.reset.password.domain.impl;

import static org.mockito.Mockito.verify;

import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class DefaultResetPasswordRepositoryDeleteTest {
	@InjectMocks
	private DefaultResetPasswordRepository defaultResetPasswordRepository;
	@Mock
	private ResetPasswordJpaRepository resetPasswordJpaRepository;
	@Mock
	private ResetPasswordEntity resetPasswordEntity;

	@Test
	public void delegatesToJpaRepositoryOne() {
		defaultResetPasswordRepository.delete(resetPasswordEntity);

		verify(resetPasswordJpaRepository).delete(resetPasswordEntity);
	}

	@Test
	public void delegatesToJpaRepositoryList() {
		defaultResetPasswordRepository.delete(Arrays.asList(resetPasswordEntity));

		verify(resetPasswordJpaRepository).delete(Arrays.asList(resetPasswordEntity));
	}
}
