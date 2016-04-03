package be.tribersoft.triber.chat.common;

import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import org.junit.After;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.RestTemplateXhrTransport;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.Transport;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

public abstract class AbstractWebsocketRestIT extends AbstractRestIT {

	private StompSession stompSession;
	final CountDownLatch latch = new CountDownLatch(1);

	public void connectWithWebsocket(String username) throws InterruptedException {

		final AtomicReference<Throwable> failure = new AtomicReference<>();
		StompSessionHandler handler = new TestAbstractStompSessionHandler(failure) {

			@Override
			public void afterConnected(final StompSession session, StompHeaders connectedHeaders) {
				stompSession = session;
				latch.countDown();
			}
		};
		connectWithWebsocket(username, handler);
		if (failure.get() != null) {
			throw new AssertionError("", failure.get());
		}
	}

	public void connectWithWebsocket(String username, StompSessionHandler handler) throws InterruptedException {
		List<Transport> transports = new ArrayList<>();
		transports.add(new WebSocketTransport(new StandardWebSocketClient()));
		RestTemplateXhrTransport xhrTransport = new RestTemplateXhrTransport(new RestTemplate());
		transports.add(xhrTransport);
		SockJsClient sockJsClient = new SockJsClient(transports);
		WebSocketStompClient stompClient = new WebSocketStompClient(sockJsClient);
		stompClient.setMessageConverter(new MappingJackson2MessageConverter());
		String jwt = tokenHandler.toToken(userRepository.getByUsername(username));
		stompClient.connect("ws://localhost:{port}/chat?jwt=" + jwt, handler, port);
		if (!latch.await(25, TimeUnit.SECONDS)) {
			fail("timeout");
		}

	}

	@After
	public void disconnectWebsocket() {
		stompSession.disconnect();
	}
}
