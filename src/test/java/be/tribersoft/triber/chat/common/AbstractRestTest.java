package be.tribersoft.triber.chat.common;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.TestPropertySource;
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
public class AbstractRestTest {

	@Value("${local.server.port}")
	private int port;

	@Inject
	private TokenHandler tokenHandler;
	@Inject
	private UserRepository userRepository;

	private String jwtHeader;

	@Before
	public void initilaizeRestAssured() {
		RestAssured.port = port;
		jwtHeader = "Bearer " + tokenHandler.toToken(userRepository.getByUsername("user"));

	}

	public String getJwtHeader() {
		return jwtHeader;
	}

}
