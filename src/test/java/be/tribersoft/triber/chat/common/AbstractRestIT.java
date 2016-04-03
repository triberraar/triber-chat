package be.tribersoft.triber.chat.common;

import javax.inject.Inject;

import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.jayway.restassured.RestAssured;

import be.tribersoft.triber.chat.Application;
import be.tribersoft.triber.chat.security.TokenHandler;
import be.tribersoft.triber.chat.user.domain.api.UserRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@TestPropertySource(value = "classpath:/application-test.properties")
@IntegrationTest("server.port:0")
@Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:db/clean.sql")
public abstract class AbstractRestIT {

	@Value("${local.server.port}")
	protected int port;

	@Inject
	protected TokenHandler tokenHandler;
	@Inject
	protected UserRepository userRepository;

	private TestMailHandler testMailHandler;

	@Before
	public void initialize() {
		RestAssured.port = port;
		testMailHandler = new TestMailHandler();
	}

	@After
	public void cleanUp() {
		testMailHandler.stop();
	}

	public String getJwtHeader(String username) {
		return "Bearer " + tokenHandler.toToken(userRepository.getByUsername(username));
	}

	public TestMailHandler getTestMailHandler() {
		return testMailHandler;
	}

}
