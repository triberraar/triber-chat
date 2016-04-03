package be.tribersoft.triber.chat.common.exception;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

import org.junit.Test;
import org.springframework.http.HttpStatus;

import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Header;

import be.tribersoft.triber.chat.common.AbstractRestIT;
import be.tribersoft.triber.chat.register.controller.ActivateRegistrationFromJsonAdapter;

public class ExceptionHandlerIT extends AbstractRestIT {

	private static final String URL = "/test/exception/{exception}";

	@Test
	public void canNotActivateUserExceptionReturnsBadRequest() {
		// @formatter:off
		given()
			.header(new Header("Authorization", getJwtHeader()))
			.pathParam("exception", "can-not-activate-user")
		.when()
			.get(URL)
		.then()
			.statusCode(HttpStatus.BAD_REQUEST.value())
			.body("errorCode", is("user.can.not.activate"));
		// @formatter:on
	}

	@Test
	public void notFoundExceptionReturnsNotFound() {
		// @formatter:off
		given()
			.header(new Header("Authorization", getJwtHeader()))
			.pathParam("exception", "not-found")
		.when()
			.get(URL)
		.then()
			.statusCode(HttpStatus.NOT_FOUND.value())
			.body("errorCode", is("error code"));
		// @formatter:on
	}

	@Test
	public void validationExceptionReturnsBadRequest() {
		// @formatter:off
		given()
			.header(new Header("Authorization", getJwtHeader()))
			.pathParam("exception", "validation")
		.when()
			.get(URL)
		.then()
			.statusCode(HttpStatus.BAD_REQUEST.value())
			.body("errors.size()", is(1))
			.body("errors[0]", is("error code"));
		// @formatter:on
	}

	@Test
	public void methodArgumentNotValidExceptionRetursBadRequest() {
		// @formatter:off
		given()
			.header(new Header("Authorization", getJwtHeader()))
			.contentType(ContentType.JSON)
			.pathParam("exception", "method-argument-not-valid")
			.body(new ActivateRegistrationFromJsonAdapter())
		.when()
			.post(URL)
		.then()
		.log().all()
			.statusCode(HttpStatus.BAD_REQUEST.value())
			.body("errors.size()", is(1))
			.body("errors[0]", is("activate.registration.validation.password.empty"));
		// @formatter:on
	}

}
