package be.tribersoft.triber.chat.user.controller;

import static com.jayway.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;

import com.google.common.collect.Sets;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Header;

import be.tribersoft.triber.chat.common.AbstractRestIT;
import be.tribersoft.triber.chat.register.controller.ActivateRegistrationFromJsonAdapter;
import be.tribersoft.triber.chat.user.domain.api.Role;
import be.tribersoft.triber.chat.user.domain.impl.UserEntity;
import be.tribersoft.triber.chat.user.domain.impl.UserJpaRepository;

public class UserControllerActivateIT extends AbstractRestIT {
	private static final String PASSWORD = "password";
	private static final String URL = "/user/{id}/activate";

	@Inject
	private UserJpaRepository userJpaRepository;

	private UserEntity user;

	@Before
	public void setUp() {
		user = new UserEntity("not activated", PASSWORD, "notactivated@mail", Sets.newHashSet(Role.ROLE_USER));
		userJpaRepository.save(user);
	}

	@Test
	public void activatesUser() {
		// @formatter:off
		given()
			.header(new Header("Authorization", getJwtHeader("admin")))
			.contentType(ContentType.JSON)
			.pathParam("id", user.getId())
			.body(new TestActivateRegistrationFromJsonAdapter())
		.when()
			.put(URL)
		.then()
			.statusCode(HttpStatus.OK.value());
		// @formatter:on

		Optional<UserEntity> foundUser = userJpaRepository.findById(user.getId());
		assertThat(foundUser).isPresent();
		assertThat(foundUser.get().isActivated()).isTrue();
	}

	private class TestActivateRegistrationFromJsonAdapter extends ActivateRegistrationFromJsonAdapter {

		@Override
		public String getPassword() {
			return PASSWORD;
		}
	}

}
