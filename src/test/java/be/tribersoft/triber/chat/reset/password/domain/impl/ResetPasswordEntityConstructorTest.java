package be.tribersoft.triber.chat.reset.password.domain.impl;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;

import org.apache.commons.lang3.time.DateUtils;
import org.junit.Test;

import be.tribersoft.triber.chat.common.DateFactory;

public class ResetPasswordEntityConstructorTest {

	private static final Date DATE = new Date();
	private static final String USER_ID = "userId";

	@Test
	public void constructs() {
		DateFactory.fixate(DATE);

		ResetPasswordEntity resetPasswordEntity = new ResetPasswordEntity(USER_ID);

		assertThat(resetPasswordEntity.getUserId()).isEqualTo(USER_ID);
		assertThat(resetPasswordEntity.getExpireDate()).isEqualTo(DateUtils.addDays(DATE, 7));
	}

}
