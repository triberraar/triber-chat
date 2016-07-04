package be.tribersoft.triber.chat.message.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;

import java.lang.reflect.Type;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandler;

import be.tribersoft.triber.chat.common.AbstractWebsocketIT;
import be.tribersoft.triber.chat.common.DateFactory;
import be.tribersoft.triber.chat.common.TestAbstractStompSessionHandler;
import be.tribersoft.triber.chat.message.domain.impl.PrivateMessageEntity;
import be.tribersoft.triber.chat.message.domain.impl.PrivateMessageJpaRepository;

public class MessageControllerPrivateChatIT extends AbstractWebsocketIT {
	private static final Date NOW = new Date();
	private static final String CONTENT = "content";
	private static final String TO = "user";
	private static final String FROM = "admin";

	@Inject
	private PrivateMessageJpaRepository privateMessageJpaRepository;

	@Before
	public void setUp() {
		DateFactory.fixate(NOW);
	}

	@Test
	public void sendsMessageToToAndFrom() throws Exception {

		final CountDownLatch latch = new CountDownLatch(2);
		final AtomicReference<Throwable> failure = new AtomicReference<>();

		StompSessionHandler fromHandler = new TestAbstractStompSessionHandler(failure) {

			@Override
			public void afterConnected(final StompSession session, StompHeaders connectedHeaders) {
				session.send("/app/message/private", new TestMessageFromJsonAdapter());
				session.subscribe("/user/topic/message/private", new StompFrameHandler() {
					@Override
					public Type getPayloadType(StompHeaders headers) {
						return PrivateMessageToJsonAdapter.class;
					}

					@Override
					public void handleFrame(StompHeaders headers, Object payload) {
						try {
							PrivateMessageToJsonAdapter message = (PrivateMessageToJsonAdapter) payload;
							assertThat(message.getContent()).isEqualTo(CONTENT);
							assertThat(message.getTimestamp()).isEqualTo(NOW);
							assertThat(message.getTo()).isEqualTo(TO);
							assertThat(message.getFrom()).isEqualTo(FROM);
						} catch (Throwable t) {
							failure.set(t);
						} finally {
							session.disconnect();
							latch.countDown();
						}
					}
				});
			}
		};
		StompSessionHandler toHandler = new TestAbstractStompSessionHandler(failure) {

			@Override
			public void afterConnected(final StompSession session, StompHeaders connectedHeaders) {
				session.subscribe("/user/topic/message/private", new StompFrameHandler() {
					@Override
					public Type getPayloadType(StompHeaders headers) {
						return PrivateMessageToJsonAdapter.class;
					}

					@Override
					public void handleFrame(StompHeaders headers, Object payload) {
						try {
							PrivateMessageToJsonAdapter message = (PrivateMessageToJsonAdapter) payload;
							assertThat(message.getContent()).isEqualTo(CONTENT);
							assertThat(message.getTimestamp()).isEqualTo(NOW);
							assertThat(message.getTo()).isEqualTo(TO);
							assertThat(message.getFrom()).isEqualTo(FROM);
						} catch (Throwable t) {
							failure.set(t);
						} finally {
							session.disconnect();
							latch.countDown();
						}
					}
				});
			}
		};

		connect(fromHandler, FROM);
		connect(toHandler, TO);

		if (!latch.await(25, TimeUnit.SECONDS)) {
			fail("timeout");
		}
		if (failure.get() != null) {
			throw new AssertionError("", failure.get());
		}

		List<PrivateMessageEntity> savedMessages = privateMessageJpaRepository.findAll();
		assertThat(savedMessages).hasSize(1);
		assertThat(savedMessages.get(0)).isInstanceOf(PrivateMessageEntity.class);
		assertThat(savedMessages.get(0).getContent()).isEqualTo(CONTENT);
		assertThat(savedMessages.get(0).getTo()).isEqualTo(TO);
		assertThat(savedMessages.get(0).getFrom()).isEqualTo(FROM);
		assertThat(new Date(savedMessages.get(0).getCreationDate().getTime())).isEqualTo(NOW);

	}

	private static class TestMessageFromJsonAdapter extends PrivateMessageFromJsonAdapter {

		@Override
		public String getContent() {
			return CONTENT;
		}

		@Override
		public String getTo() {
			return TO;
		}

		@Override
		public String getFrom() {
			return FROM;
		}
	}

}
