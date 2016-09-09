package be.tribersoft.triber.chat.room.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;

import java.lang.reflect.Type;
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
import be.tribersoft.triber.chat.common.TestAbstractStompSessionHandler;
import be.tribersoft.triber.chat.room.domain.impl.RoomEntity;
import be.tribersoft.triber.chat.room.domain.impl.RoomJpaRepository;

public class RoomControllerInviteIT extends AbstractWebsocketIT {

	private static final String ROOM = "room";
	private static final String USERNAME = "user";
	private static final String OWNER = "admin";

	@Inject
	private RoomJpaRepository roomJpaRepository;
	private RoomEntity room;

	@Before
	public void setUp() {
		room = new RoomEntity(OWNER, "room");
		roomJpaRepository.save(room);
	}

	@Test
	public void createsRoom() throws InterruptedException {
		final CountDownLatch latch = new CountDownLatch(2);
		final AtomicReference<Throwable> failure = new AtomicReference<>();

		StompSessionHandler participantHandler = new TestAbstractStompSessionHandler(failure) {
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
							assertThat(message.getName()).isEqualTo(ROOM);
							assertThat(message.getOwner()).isEqualTo(OWNER);
							assertThat(message.getParticipants()).hasSize(1).contains(USERNAME);
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

		StompSessionHandler ownerHandler = new TestAbstractStompSessionHandler(failure) {
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
							assertThat(message.getName()).isEqualTo(ROOM);
							assertThat(message.getOwner()).isEqualTo(OWNER);
							assertThat(message.getParticipants()).hasSize(1).contains(USERNAME);
						} catch (Throwable t) {
							failure.set(t);
						} finally {
							session.disconnect();
							latch.countDown();
						}
					}
				});
				session.send("/app/room/invite", new TestRoomInvitationFromJsonAdapter());
			}
		};

		connect(ownerHandler, OWNER);
		connect(participantHandler, USERNAME);
		if (!latch.await(25, TimeUnit.SECONDS)) {
			fail("timeout");
		}
		if (failure.get() != null) {
			throw new AssertionError("", failure.get());
		}

		List<RoomEntity> savedRooms = roomJpaRepository.findAll();
		assertThat(savedRooms).hasSize(1);
		assertThat(savedRooms.get(0).getName()).isEqualTo(ROOM);
		assertThat(savedRooms.get(0).getParticipants()).hasSize(1).contains(USERNAME);
	}

	private class TestRoomInvitationFromJsonAdapter extends RoomInvitationFromJsonAdapter {

		@Override
		public String getId() {
			return room.getId();
		}

		@Override
		public String getParticipant() {
			return USERNAME;
		}
	}

}
