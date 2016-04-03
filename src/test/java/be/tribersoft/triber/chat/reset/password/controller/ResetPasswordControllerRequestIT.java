package be.tribersoft.triber.chat.reset.password.controller;

import static com.jayway.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;

import javax.inject.Inject;

import org.apache.commons.lang3.time.DateUtils;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;

import com.jayway.restassured.http.ContentType;

import be.tribersoft.triber.chat.common.AbstractRestIT;
import be.tribersoft.triber.chat.common.DateFactory;
import be.tribersoft.triber.chat.reset.password.domain.impl.ResetPasswordEntity;
import be.tribersoft.triber.chat.reset.password.domain.impl.ResetPasswordJpaRepository;
import be.tribersoft.triber.chat.user.domain.api.Role;
import be.tribersoft.triber.chat.user.domain.impl.UserEntity;
import be.tribersoft.triber.chat.user.domain.impl.UserJpaRepository;

public class ResetPasswordControllerRequestIT extends AbstractRestIT {
	private static final String PASSWORD = "resetpassword";

	private static final Date NOW = new Date();

	private static final String EMAIL = "resetpassword@email.be";

	private static final String URL = "/reset-password";

	@Inject
	private ResetPasswordJpaRepository resetPasswordJpaRepository;
	@Inject
	private UserJpaRepository userJpaRepository;

	private UserEntity userEntity;

	@Before
	public void setUp() {
		userEntity = new UserEntity("resetpassword user", PASSWORD, EMAIL, new HashSet<>(Arrays.asList(Role.ROLE_USER)));
		userEntity.activate(PASSWORD);
		userJpaRepository.save(userEntity);
		DateFactory.fixate(NOW);
	}

	@Test
	public void requestsPasswordReset() {
		// @formatter:off
		given()
			.body(new TestResetPasswordRequestFromJsonAdapter())
			.contentType(ContentType.JSON)
		.when()
			.post(URL)
		.then()
			.statusCode(HttpStatus.OK.value());
		// @formatter:on

		assertThat(getTestMailHandler().hasMessage()).isTrue();
		assertThat(resetPasswordJpaRepository.findAll()).hasSize(1);
		ResetPasswordEntity resetPasswordEntity = resetPasswordJpaRepository.findAll().get(0);
		assertThat(resetPasswordEntity.getUserId()).isEqualTo(userEntity.getId());
		assertThat(new Date(resetPasswordEntity.getExpireDate().getTime())).isEqualTo(DateUtils.addDays(NOW, 7));
	}

	private class TestResetPasswordRequestFromJsonAdapter extends ResetPasswordRequestFromJsonAdapter {
		@Override
		public String getEmail() {
			return EMAIL;
		}
	}

}
