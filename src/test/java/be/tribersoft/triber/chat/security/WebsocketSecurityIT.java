package be.tribersoft.triber.chat.security;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.RestTemplateXhrTransport;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.Transport;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

import be.tribersoft.triber.chat.Application;
import be.tribersoft.triber.chat.common.TestAbstractStompSessionHandler;
import be.tribersoft.triber.chat.user.domain.api.UserRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@TestPropertySource(value = "classpath:/application-test.properties")
@IntegrationTest("server.port:0")
public class WebsocketSecurityIT {
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

	@Test
	public void loggedInUserCanProceed() throws InterruptedException {
		final CountDownLatch latch = new CountDownLatch(1);
		final AtomicReference<Throwable> failure = new AtomicReference<>();
		StompSessionHandler handler = new TestAbstractStompSessionHandler(failure) {

			@Override
			public void afterConnected(final StompSession session, StompHeaders connectedHeaders) {
				session.disconnect();
				latch.countDown();
			}
		};
		WebSocketStompClient stompClient = new WebSocketStompClient(sockJsClient);
		stompClient.setMessageConverter(new MappingJackson2MessageConverter());
		String jwt = tokenHandler.toToken(userRepository.getByUsername("user"));
		stompClient.connect("ws://localhost:{port}/chat?jwt=" + jwt, handler, port);
		if (!latch.await(25, TimeUnit.SECONDS)) {
			fail("timeout");
		}
		if (failure.get() != null) {
			throw new AssertionError("", failure.get());
		}
	}

	@Test
	public void notLoggedInUserGetsForbidden() throws InterruptedException {
		final CountDownLatch latch = new CountDownLatch(1);
		final AtomicReference<Throwable> failure = new AtomicReference<>();
		StompSessionHandler handler = new TestAbstractStompSessionHandler(failure) {

			@Override
			public void afterConnected(final StompSession session, StompHeaders connectedHeaders) {
				session.disconnect();
				latch.countDown();
			}
		};
		WebSocketStompClient stompClient = new WebSocketStompClient(sockJsClient);
		stompClient.setMessageConverter(new MappingJackson2MessageConverter());
		ListenableFuture<StompSession> connect = stompClient.connect("ws://localhost:{port}/chat", handler, port);
		connect.addCallback(result -> {
			latch.countDown();
		}, ex -> {
			failure.set(ex);
			latch.countDown();
		});

		if (!latch.await(25, TimeUnit.SECONDS)) {
			fail("timeout");
		}
		if (failure.get() != null && failure.get() instanceof HttpClientErrorException) {
			HttpClientErrorException error = (HttpClientErrorException) failure.get();
			assertThat(error.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
			return;
		}
		fail("didn't get forbidden");
	}
}
