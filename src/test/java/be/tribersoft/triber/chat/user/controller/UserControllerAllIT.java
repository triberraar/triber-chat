package be.tribersoft.triber.chat.user.controller;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

import org.junit.Test;
import org.springframework.http.HttpStatus;

import com.jayway.restassured.response.Header;

import be.tribersoft.triber.chat.common.AbstractRestIT;

public class UserControllerAllIT extends AbstractRestIT {

	private static final String URL = "/user";

	@Test
	public void returnsAllUsersPaged() {
		// @formatter:off
		given()
			.header(new Header("Authorization", getJwtHeader("admin")))
		.when()
			.get(URL)
		.then()
			.statusCode(HttpStatus.OK.value())
			.body("totalPages", is(1))
			.body("last", is(true))
			.body("totalElements", is(3))
			.body("numberOfElements", is(3))
			.body("first", is(true))
			.body("size", is(20))
			.body("content.size()", is(3))
			.body("content[0].size()", is(5))
			.body("content[0].id", is("1"))
			.body("content[0].username", is("admin"))
			.body("content[0].email", is("admin@admin.com"))
			.body("content[0].validated", is(true))
			.body("content[0].activated", is(true))
			.body("content[1].size()", is(5))
			.body("content[1].id", is("3"))
			.body("content[1].username", is("unvalidated"))
			.body("content[1].email", is("unvalidated@user.com"))
			.body("content[1].validated", is(false))
			.body("content[1].activated", is(true))
			.body("content[2].size()", is(5))
			.body("content[2].id", is("2"))
			.body("content[2].username", is("user"))
			.body("content[2].email", is("user2@user.com"))
			.body("content[2].validated", is(true))
			.body("content[2].activated", is(true));
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

	@Test
	public void canFilterOnUsername() {
		// @formatter:off
		given()
			.header(new Header("Authorization", getJwtHeader("admin")))
			.queryParam("username", "admin")
		.when()
			.get(URL)
		.then()
			.statusCode(HttpStatus.OK.value())
			.body("content.size()", is(1))
			.body("content[0].size()", is(5))
			.body("content[0].id", is("1"))
			.body("content[0].username", is("admin"))
			.body("content[0].email", is("admin@admin.com"))
			.body("content[0].validated", is(true))
			.body("content[0].activated", is(true));
		// @formatter:on

	}

	@Test
	public void canFilterOnEmail() {
		// @formatter:off
		given()
			.header(new Header("Authorization", getJwtHeader("admin")))
			.queryParam("email", "admin@admin.com")
		.when()
			.get(URL)
		.then()
			.statusCode(HttpStatus.OK.value())
			.body("content.size()", is(1))
			.body("content[0].size()", is(5))
			.body("content[0].id", is("1"))
			.body("content[0].username", is("admin"))
			.body("content[0].email", is("admin@admin.com"))
			.body("content[0].validated", is(true))
			.body("content[0].activated", is(true));
		// @formatter:on

	}

	@Test
	public void canFilterOnActivated() {
		// @formatter:off
		given()
			.header(new Header("Authorization", getJwtHeader("admin")))
			.queryParam("activated", "false")
		.when()
			.get(URL)
		.then()
			.statusCode(HttpStatus.OK.value())
			.body("content.size()", is(0));
		// @formatter:on

	}

	@Test
	public void canFilterOnValidated() {
		// @formatter:off
		given()
			.header(new Header("Authorization", getJwtHeader("admin")))
			.queryParam("validated", "false")
		.when()
			.get(URL)
		.then()
			.statusCode(HttpStatus.OK.value())
			.body("content.size()", is(1))
			.body("content[0].size()", is(5))
			.body("content[0].id", is("3"))
			.body("content[0].username", is("unvalidated"))
			.body("content[0].email", is("unvalidated@user.com"))
			.body("content[0].validated", is(false))
			.body("content[0].activated", is(true));
		// @formatter:on

	}
}
