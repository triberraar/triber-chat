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
	private int port;

	@Inject
	private TokenHandler tokenHandler;
	@Inject
	private UserRepository userRepository;

	private String jwtHeader;

	private TestMailHandler testMailHandler;

	@Before
	public void initialize() {
		RestAssured.port = port;
		jwtHeader = "Bearer " + tokenHandler.toToken(userRepository.getByUsername("user"));
		testMailHandler = new TestMailHandler();
	}

	@After
	public void cleanUp() {
		testMailHandler.stop();
	}

	public String getJwtHeader() {
		return jwtHeader;
	}

	public TestMailHandler getTestMailHandler() {
		return testMailHandler;
	}

}
