package be.tribersoft.triber.chat.security;

import static com.jayway.restassured.RestAssured.given;
import static com.jayway.restassured.RestAssured.when;

import org.junit.Test;
import org.springframework.http.HttpStatus;

import com.jayway.restassured.response.Header;

import be.tribersoft.triber.chat.common.AbstractRestTest;

public class RestSecurityIT extends AbstractRestTest {

	private static final String URL = "/test/security";

	@Test
	public void loggedInUserCanProceed() {
		String jwtHeader = getJwtHeader();
		// @formatter:off
		given()
			.header(new Header("Authorization", jwtHeader))
		.when()
			.get(URL)
		.then()
			.statusCode(HttpStatus.OK.value());
		// @formatter:on
	}

	@Test
	public void notLoggedInUserGetsForbidden() {
		// @formatter:off
		when()
			.get(URL)
		.then()
			.statusCode(HttpStatus.FORBIDDEN.value());
		// @formatter:on
	}

}
