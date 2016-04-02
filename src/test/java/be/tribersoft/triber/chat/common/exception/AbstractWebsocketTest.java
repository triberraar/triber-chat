package be.tribersoft.triber.chat.common.exception;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.RestTemplateXhrTransport;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.Transport;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

import be.tribersoft.triber.chat.Application;
import be.tribersoft.triber.chat.security.TokenHandler;
import be.tribersoft.triber.chat.user.domain.api.UserRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@TestPropertySource(value = "classpath:/application-test.properties")
@IntegrationTest("server.port:0")
public class AbstractWebsocketTest {

	@Inject
	private TokenHandler tokenHandler;
	@Inject
	private UserRepository userRepository;

	private SockJsClient sockJsClient;
	@Value("${local.server.port}")
	private int port;

	@Before
	public void createSockJsClient() {
		List<Transport> transports = new ArrayList<>();
		transports.add(new WebSocketTransport(new StandardWebSocketClient()));
		RestTemplateXhrTransport xhrTransport = new RestTemplateXhrTransport(new RestTemplate());
		transports.add(xhrTransport);
		sockJsClient = new SockJsClient(transports);
	}

	protected void connect(StompSessionHandler handler, String username) {
		WebSocketStompClient stompClient = new WebSocketStompClient(sockJsClient);
		stompClient.setMessageConverter(new MappingJackson2MessageConverter());
		String jwt = tokenHandler.toToken(userRepository.getByUsername(username));
		stompClient.connect("ws://localhost:{port}/chat?jwt=" + jwt, handler, port);
	}

}
