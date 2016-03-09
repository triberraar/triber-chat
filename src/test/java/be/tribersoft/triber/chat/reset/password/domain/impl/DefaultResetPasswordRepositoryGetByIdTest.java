package be.tribersoft.triber.chat.reset.password.domain.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import be.tribersoft.triber.chat.reset.password.domain.api.ResetPasswordNotFoundException;

@RunWith(MockitoJUnitRunner.class)
public class DefaultResetPasswordRepositoryGetByIdTest {

	private static final String ID = "id";

	@InjectMocks
	private DefaultResetPasswordRepository defaultResetPasswordRepository;
	@Mock
	private ResetPasswordJpaRepository resetPasswordJpaRepository;
	@Mock
	private ResetPasswordEntity resetPasswordEntity;

	@Test(expected = ResetPasswordNotFoundException.class)
	public void failsWhenResetPasswordNotFound() {
		when(resetPasswordJpaRepository.findById(ID)).thenReturn(Optional.<ResetPasswordEntity> empty());

		defaultResetPasswordRepository.getById(ID);
	}

	@Test
	public void returnsWhenFound() {
		when(resetPasswordJpaRepository.findById(ID)).thenReturn(Optional.of(resetPasswordEntity));

		assertThat(defaultResetPasswordRepository.getById(ID)).isEqualTo(resetPasswordEntity);
	}
}
