package be.tribersoft.triber.chat.reset.password.domain.impl;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;

import org.apache.commons.lang3.time.DateUtils;
import org.junit.Before;
import org.junit.Test;
import org.mockito.internal.util.reflection.Whitebox;

import be.tribersoft.triber.chat.common.DateFactory;

public class ResetPasswordEntityIsValidTest {

	private static final Date NOW = new Date();

	@Before
	public void setUp() {
		DateFactory.fixate(NOW);
	}

	@Test
	public void returnsTrueWhenExpireDateAfterNow() {
		ResetPasswordEntity resetPasswordEntity = new ResetPasswordEntity("userId");
		Whitebox.setInternalState(resetPasswordEntity, "expireDate", DateUtils.addMilliseconds(NOW, 1));

		assertThat(resetPasswordEntity.isValid()).isTrue();
	}

	@Test
	public void returnsFalseWhenExpireDateBeforeNow() {
		ResetPasswordEntity resetPasswordEntity = new ResetPasswordEntity("userId");
		Whitebox.setInternalState(resetPasswordEntity, "expireDate", NOW);

		assertThat(resetPasswordEntity.isValid()).isFalse();
	}

	@Test
	public void returnsFalseWhenExpireDateIsNow() {
		ResetPasswordEntity resetPasswordEntity = new ResetPasswordEntity("userId");
		Whitebox.setInternalState(resetPasswordEntity, "expireDate", DateUtils.addMilliseconds(NOW, -1));

		assertThat(resetPasswordEntity.isValid()).isFalse();
	}

}
