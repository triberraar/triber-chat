package be.tribersoft.triber.chat.register.controller;

import static com.jayway.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;

import javax.inject.Inject;

import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.jayway.restassured.http.ContentType;

import be.tribersoft.triber.chat.common.AbstractRestIT;
import be.tribersoft.triber.chat.common.DummyCaptchaVerificator;
import be.tribersoft.triber.chat.user.domain.api.Role;
import be.tribersoft.triber.chat.user.domain.impl.UserEntity;
import be.tribersoft.triber.chat.user.domain.impl.UserJpaRepository;

public class RegisterControllerIT extends AbstractRestIT {
	private static final String USERNAME = "username";
	private static final String PASSWORD = "password";
	private static final String EMAIL = "email@email";
	private static final String CAPTCHA = "captcha";
	private static final String URL = "/register";

	@Inject
	private DummyCaptchaVerificator dummyCaptchaVerificator;
	@Inject
	private UserJpaRepository userJpaRepository;

	@Test
	public void registersUser() {
		// @formatter:off
		given()
			.body(new TestFromJsonAdapter())
			.contentType(ContentType.JSON)
		.when()
			.post(URL)
		.then()
			.statusCode(HttpStatus.OK.value());
		// @formatter:on

		assertThat(dummyCaptchaVerificator.isCalled()).isTrue();
		assertThat(dummyCaptchaVerificator.getCaptcha()).isEqualTo(CAPTCHA);
		assertThat(getTestMailHandler().hasMessage()).isTrue();
		Optional<UserEntity> user = userJpaRepository.findByEmail(EMAIL);
		assertThat(user).isPresent();
		assertThat(user.get().getEmail()).isEqualTo(EMAIL);
		assertThat(new BCryptPasswordEncoder().matches(PASSWORD, user.get().getPassword())).isTrue();
		assertThat(user.get().getRoles()).isEqualTo(new HashSet<>(Arrays.asList(Role.ROLE_USER)));
		assertThat(user.get().getUsername()).isEqualTo(USERNAME);
		assertThat(user.get().isActivated()).isFalse();
		assertThat(user.get().isValidated()).isFalse();

	}

	private class TestFromJsonAdapter extends RegisterFromJsonAdapter {

		@Override
		public String getCaptcha() {
			return CAPTCHA;
		}

		@Override
		public String getEmail() {
			return EMAIL;
		}

		@Override
		public String getPassword() {
			return PASSWORD;
		}

		@Override
		public String getUsername() {
			return USERNAME;
		}
	}
}
