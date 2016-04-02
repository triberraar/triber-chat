package be.tribersoft.triber.chat.common;

import java.util.concurrent.atomic.AtomicReference;

import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;

public class TestAbstractStompSessionHandler extends StompSessionHandlerAdapter {
	private final AtomicReference<Throwable> failure;

	public TestAbstractStompSessionHandler(AtomicReference<Throwable> failure) {
		this.failure = failure;
	}

	@Override
	public void handleFrame(StompHeaders headers, Object payload) {
		this.failure.set(new Exception(headers.toString()));
	}

	@Override
	public void handleException(StompSession s, StompCommand c, StompHeaders h, byte[] p, Throwable ex) {
		this.failure.set(ex);
	}

	@Override
	public void handleTransportError(StompSession session, Throwable ex) {
		this.failure.set(ex);
	}
}
