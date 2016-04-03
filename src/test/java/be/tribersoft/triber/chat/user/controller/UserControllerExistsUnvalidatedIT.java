package be.tribersoft.triber.chat.user.controller;

import static com.jayway.restassured.RestAssured.given;

import javax.inject.Inject;

import org.junit.Test;
import org.springframework.http.HttpStatus;

import com.jayway.restassured.response.Header;

import be.tribersoft.triber.chat.common.AbstractRestIT;
import be.tribersoft.triber.chat.user.domain.impl.UserJpaRepository;

public class UserControllerExistsUnvalidatedIT extends AbstractRestIT {

	private static final String URL = "/user/unvalidated";

	@Inject
	private UserJpaRepository userJpaRepository;

	@Test
	public void returnsOkWhenUnvalidatedUserExists() {
		// @formatter:off
		given()
			.header(new Header("Authorization", getJwtHeader("admin")))
		.when()
			.get(URL)
		.then()
			.statusCode(HttpStatus.OK.value());
		// @formatter:on

	}

	@Test
	public void failsWithNotFoundWhenNoUnvalidatedUserExists() {
		userJpaRepository.delete("3");
		// @formatter:off
		given()
			.header(new Header("Authorization", getJwtHeader("admin")))
		.when()
			.get(URL)
		.then()
			.statusCode(HttpStatus.NOT_FOUND.value());
		// @formatter:on

	}

	@Test
	public void forbiddenForUserRole() {
		// @formatter:off
		given()
			.header(new Header("Authorization", getJwtHeader("user")))
		.when()
			.get(URL)
		.then()
			.statusCode(HttpStatus.FORBIDDEN.value());
		// @formatter:on

	}

}
