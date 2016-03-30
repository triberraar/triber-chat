package be.tribersoft.triber.chat.common.exception;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Header;

import be.tribersoft.triber.chat.Application;
import be.tribersoft.triber.chat.register.controller.ActivateRegistrationFromJsonAdapter;
import be.tribersoft.triber.chat.security.TokenHandler;
import be.tribersoft.triber.chat.user.domain.api.UserRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@TestPropertySource(value = "classpath:/application-test.properties")
@IntegrationTest("server.port:0")
public class ExceptionHandlerIT {

	private static final String URL = "/test/exception/{exception}";

	@Value("${local.server.port}")
	private int port;

	@Inject
	private TokenHandler tokenHandler;
	@Inject
	private UserRepository userRepository;

	@Before
	public void setUp() {
		RestAssured.port = port;

	}

	@Test
	public void canNotActivateUserExceptionReturnsBadRequest() {
		String jwtHeader = "Bearer " + tokenHandler.toToken(userRepository.getByUsername("user"));

		// @formatter:off
		given()
			.header(new Header("Authorization", jwtHeader))
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
		String jwtHeader = "Bearer " + tokenHandler.toToken(userRepository.getByUsername("user"));

		// @formatter:off
		given()
			.header(new Header("Authorization", jwtHeader))
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
		String jwtHeader = "Bearer " + tokenHandler.toToken(userRepository.getByUsername("user"));

		// @formatter:off
		given()
			.header(new Header("Authorization", jwtHeader))
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
		String jwtHeader = "Bearer " + tokenHandler.toToken(userRepository.getByUsername("user"));

		// @formatter:off
		given()
			.header(new Header("Authorization", jwtHeader))
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
