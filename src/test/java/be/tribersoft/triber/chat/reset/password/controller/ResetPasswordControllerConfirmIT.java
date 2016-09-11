package be.tribersoft.triber.chat.reset.password.controller;

import static com.jayway.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;
import java.util.Optional;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.google.common.collect.Sets;
import com.jayway.restassured.http.ContentType;

import be.tribersoft.triber.chat.common.AbstractRestIT;
import be.tribersoft.triber.chat.common.DateFactory;
import be.tribersoft.triber.chat.reset.password.domain.impl.ResetPasswordEntity;
import be.tribersoft.triber.chat.reset.password.domain.impl.ResetPasswordJpaRepository;
import be.tribersoft.triber.chat.user.domain.api.Role;
import be.tribersoft.triber.chat.user.domain.impl.UserEntity;
import be.tribersoft.triber.chat.user.domain.impl.UserJpaRepository;

public class ResetPasswordControllerConfirmIT extends AbstractRestIT {
	private static final String PASSWORD = "resetpassword";

	private static final Date NOW = new Date();
	private static final String EMAIL = "resetpassword@email.be";
	private static final String NEW_PASSWORD = "new password";

	private static final String URL = "/reset-password/{id}";

	@Inject
	private ResetPasswordJpaRepository resetPasswordJpaRepository;
	@Inject
	private UserJpaRepository userJpaRepository;

	private UserEntity userEntity;
	private ResetPasswordEntity resetPasswordEntity;

	@Before
	public void setUp() {
		userEntity = new UserEntity("resetpassword user", PASSWORD, EMAIL, Sets.newHashSet(Role.ROLE_USER));
		userEntity.activate(PASSWORD);
		userJpaRepository.save(userEntity);
		resetPasswordEntity = new ResetPasswordEntity(userEntity.getId());
		resetPasswordJpaRepository.save(resetPasswordEntity);
		DateFactory.fixate(NOW);
	}

	@Test
	public void requestsPasswordReset() {
		// @formatter:off
		given()
			.body(new TestResetPasswordConfirmationFromJsonAdapter())
			.pathParam("id", resetPasswordEntity.getId())
			.contentType(ContentType.JSON)
		.when()
			.post(URL)
		.then()
			.statusCode(HttpStatus.OK.value());
		// @formatter:on

		assertThat(resetPasswordJpaRepository.findAll()).isEmpty();
		Optional<UserEntity> foundUserEntity = userJpaRepository.findById(userEntity.getId());
		assertThat(foundUserEntity).isPresent();
		assertThat(new BCryptPasswordEncoder().matches(NEW_PASSWORD, foundUserEntity.get().getPassword())).isTrue();
	}

	private class TestResetPasswordConfirmationFromJsonAdapter extends ResetPasswordConfirmationFromJsonAdapter {

		@Override
		public String getPassword() {
			return NEW_PASSWORD;
		}
	}

}
