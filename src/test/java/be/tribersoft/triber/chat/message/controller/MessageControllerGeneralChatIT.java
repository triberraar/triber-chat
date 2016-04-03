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
import be.tribersoft.triber.chat.message.domain.impl.AbstractMessageEntity;
import be.tribersoft.triber.chat.message.domain.impl.MessageJpaRepository;
import be.tribersoft.triber.chat.message.domain.impl.PublicMessageEntity;

public class MessageControllerGeneralChatIT extends AbstractWebsocketIT {
	private static final String USERNAME = "user";
	private static final Date NOW = new Date();
	private static final String CONTENT = "content";

	@Inject
	private MessageJpaRepository messageJpaRepository;

	@Before
	public void setUp() {
		DateFactory.fixate(NOW);
	}

	@Test
	public void receivesSendMessage() throws Exception {

		final CountDownLatch latch = new CountDownLatch(1);
		final AtomicReference<Throwable> failure = new AtomicReference<>();

		StompSessionHandler handler = new TestAbstractStompSessionHandler(failure) {

			@Override
			public void afterConnected(final StompSession session, StompHeaders connectedHeaders) {
				session.subscribe("/topic/message/general", new StompFrameHandler() {
					@Override
					public Type getPayloadType(StompHeaders headers) {
						return MessageToJsonAdapter.class;
					}

					@Override
					public void handleFrame(StompHeaders headers, Object payload) {
						try {
							MessageToJsonAdapter message = (MessageToJsonAdapter) payload;
							assertThat(message.getContent()).isEqualTo(CONTENT);
							assertThat(message.getUsername()).isEqualTo(USERNAME);
							assertThat(message.getTimestamp()).isEqualTo(NOW);
						} catch (Throwable t) {
							failure.set(t);
						} finally {
							session.disconnect();
							latch.countDown();
						}
					}
				});
				session.send("/app/message/general", new TestMessageFromJsonAdapter());
			}
		};

		connect(handler, USERNAME);

		if (!latch.await(25, TimeUnit.SECONDS)) {
			fail("timeout");
		}
		if (failure.get() != null) {
			throw new AssertionError("", failure.get());
		}

		List<AbstractMessageEntity> savedMessages = messageJpaRepository.findAll();
		assertThat(savedMessages).hasSize(1);
		assertThat(savedMessages.get(0)).isInstanceOf(PublicMessageEntity.class);
		assertThat(savedMessages.get(0).getContent()).isEqualTo(CONTENT);
		assertThat(savedMessages.get(0).getOwnerUsername()).isEqualTo(USERNAME);
		assertThat(new Date(savedMessages.get(0).getCreationDate().getTime())).isEqualTo(NOW);

	}

	private static class TestMessageFromJsonAdapter extends MessageFromJsonAdapter {

		@Override
		public String getContent() {
			return CONTENT;
		}
	}

}
