package be.tribersoft.triber.chat.user.controller;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

import java.util.concurrent.ConcurrentHashMap;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;
import org.mockito.internal.util.reflection.Whitebox;
import org.springframework.http.HttpStatus;

import com.jayway.restassured.response.Header;

import be.tribersoft.triber.chat.common.AbstractWebsocketRestIT;
import be.tribersoft.triber.chat.user.domain.api.ConnectedUsersRepository;

public class UserControllerAllConnectedIT extends AbstractWebsocketRestIT {
	private static final String URL = "/user/connected";

	@Inject
	private ConnectedUsersRepository connectedUsersRepository;

	@Before
	public void setUp() {
		Whitebox.setInternalState(connectedUsersRepository, "concurrentUsers", new ConcurrentHashMap<>());
	}

	@Test
	public void returnsAllConnectedUsers() throws InterruptedException {
		connectWithWebsocket("user");
		// @formatter:off
		given()
			.header(new Header("Authorization", getJwtHeader("admin")))
		.when()
			.get(URL)
		.then()
			.statusCode(HttpStatus.OK.value())
			.body("size()", is(1))
			.body("[0].size()", is(5))
			.body("[0].id", is("2"))
			.body("[0].username", is("user"))
			.body("[0].email", is("user2@user.com"))
			.body("[0].validated", is(true))
			.body("[0].activated", is(true));
		// @formatter:on
	}
}
