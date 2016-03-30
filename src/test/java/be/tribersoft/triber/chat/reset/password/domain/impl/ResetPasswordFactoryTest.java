package be.tribersoft.triber.chat.reset.password.domain.impl;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;

import org.apache.commons.lang3.time.DateUtils;
import org.junit.Before;
import org.junit.Test;

import be.tribersoft.triber.chat.common.DateFactory;

public class ResetPasswordFactoryTest {

	private static final Date NOW = new Date();

	private static final String USER_ID = "user id";

	private ResetPasswordFactory resetPasswordFactory = new ResetPasswordFactory();

	@Before
	public void setUp() {
		DateFactory.fixate(NOW);
	}

	@Test
	public void returnsANewResetPasswordEntity() {
		ResetPasswordEntity result = resetPasswordFactory.create(USER_ID);

		assertThat(result.getUserId()).isEqualTo(USER_ID);
		assertThat(result.getExpireDate()).isEqualTo(DateUtils.addDays(NOW, 7));
	}

}
