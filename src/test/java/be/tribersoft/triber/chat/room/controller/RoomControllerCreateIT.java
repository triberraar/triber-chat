package be.tribersoft.triber.chat.room.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;

import java.lang.reflect.Type;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import javax.inject.Inject;

import org.junit.Test;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandler;

import be.tribersoft.triber.chat.common.AbstractWebsocketIT;
import be.tribersoft.triber.chat.common.TestAbstractStompSessionHandler;
import be.tribersoft.triber.chat.room.domain.impl.RoomEntity;
import be.tribersoft.triber.chat.room.domain.impl.RoomJpaRepository;

public class RoomControllerCreateIT extends AbstractWebsocketIT {

	private static final String NAME = "name";
	private static final String USERNAME = "user";

	@Inject
	private RoomJpaRepository roomJpaRepository;

	@Test
	public void createsRoom() throws InterruptedException {
		final CountDownLatch latch = new CountDownLatch(1);
		final AtomicReference<Throwable> failure = new AtomicReference<>();

		StompSessionHandler handler = new TestAbstractStompSessionHandler(failure) {
			@Override
			public void afterConnected(final StompSession session, StompHeaders connectedHeaders) {
				session.subscribe("/user/topic/room/status", new StompFrameHandler() {
					@Override
					public Type getPayloadType(StompHeaders headers) {
						return RoomToJsonAdapter.class;
					}

					@Override
					public void handleFrame(StompHeaders headers, Object payload) {
						try {
							RoomToJsonAdapter message = (RoomToJsonAdapter) payload;
							assertThat(message.getName()).isEqualTo(NAME);
							assertThat(message.getOwner()).isEqualTo(USERNAME);
						} catch (Throwable t) {
							failure.set(t);
						} finally {
							session.disconnect();
							latch.countDown();
						}
					}
				});

				session.send("/app/room/create", new TestRoomFromJsonAdapter());
			}
		};

		connect(handler, USERNAME);
		if (!latch.await(25, TimeUnit.SECONDS)) {
			fail("timeout");
		}
		if (failure.get() != null) {
			throw new AssertionError("", failure.get());
		}

		List<RoomEntity> savedRooms = roomJpaRepository.findAll();
		assertThat(savedRooms).hasSize(1);
		assertThat(savedRooms.get(0).getName()).isEqualTo(NAME);
		assertThat(savedRooms.get(0).getParticipants()).isEmpty();
	}

	private static class TestRoomFromJsonAdapter extends RoomFromJsonAdapter {

		@Override
		public String getName() {
			return NAME;
		}
	}

}
