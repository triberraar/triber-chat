package be.tribersoft.triber.chat.room.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Set;
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
import be.tribersoft.triber.chat.user.domain.impl.DefaultUserRepository;
import be.tribersoft.triber.chat.user.domain.impl.UserEntity;
import be.tribersoft.triber.chat.user.domain.impl.UserJpaRepository;

public class RoomConnectedUserListenerUserDisconnectedIT extends AbstractWebsocketIT {

	private static final String UNVALIDATED_ROOM = "unvalidated room";
	private static final String UNVALIDATED = "unvalidated";
	private static final String USER = "user";
	private static final String ADMIN_ROOM = "admin room";
	private static final String ADMIN = "admin";
	@Inject
	private DefaultUserRepository defaultUserRepository;
	@Inject
	private UserJpaRepository userJpaRepository;
	@Inject
	private RoomJpaRepository roomJpaRepository;
	private RoomEntity adminRoom;
	private RoomEntity unvalidatedRoom;

	@Before
	public void setUp() {
		UserEntity userEntity = defaultUserRepository.getById("3");
		userEntity.validate();
		userJpaRepository.save(userEntity);

		adminRoom = new RoomEntity(ADMIN, ADMIN_ROOM);
		adminRoom.addParticipant(USER);

		unvalidatedRoom = new RoomEntity(UNVALIDATED, UNVALIDATED_ROOM);
		unvalidatedRoom.addParticipant(USER);
		unvalidatedRoom.addParticipant(ADMIN);

		roomJpaRepository.save(adminRoom);
		roomJpaRepository.save(unvalidatedRoom);
	}

	@Test
	public void removesUserFromRoomAndNotifies() throws InterruptedException {
		final CountDownLatch latch = new CountDownLatch(3);
		final AtomicReference<Throwable> adminFailure = new AtomicReference<>();
		final AtomicReference<Throwable> userFailure = new AtomicReference<>();
		final AtomicReference<Throwable> unvalidatedFailure = new AtomicReference<>();

		StompSessionHandler adminHandler = new TestAbstractStompSessionHandler(adminFailure) {
			@Override
			public void afterConnected(final StompSession session, StompHeaders connectedHeaders) {
				session.disconnect();
			}
		};
		StompSessionHandler userHandler = new TestAbstractStompSessionHandler(userFailure) {
			@Override
			public void afterConnected(final StompSession session, StompHeaders connectedHeaders) {

				session.subscribe("/user/topic/room/deleted", new StompFrameHandler() {
					@Override
					public Type getPayloadType(StompHeaders headers) {
						return RoomToJsonAdapter.class;
					}

					@Override
					public void handleFrame(StompHeaders headers, Object payload) {
						try {
							RoomToJsonAdapter message = (RoomToJsonAdapter) payload;
							assertThat(message.getName()).isEqualTo(ADMIN_ROOM);
							assertThat(message.getOwner()).isEqualTo(ADMIN);
							assertThat(message.getParticipants()).hasSize(1).contains(USER);
						} catch (Throwable t) {
							userFailure.set(t);
						} finally {
							latch.countDown();
						}
					}
				});
				session.subscribe("/user/topic/room/status", new StompFrameHandler() {
					@Override
					public Type getPayloadType(StompHeaders headers) {
						return RoomToJsonAdapter.class;
					}

					@Override
					public void handleFrame(StompHeaders headers, Object payload) {
						try {
							RoomToJsonAdapter message = (RoomToJsonAdapter) payload;
							assertThat(message.getName()).isEqualTo(UNVALIDATED_ROOM);
							assertThat(message.getOwner()).isEqualTo(UNVALIDATED);
							assertThat(message.getParticipants()).hasSize(1).contains(USER);
						} catch (Throwable t) {
							userFailure.set(t);
						} finally {
							latch.countDown();
						}
					}
				});
			}

		};
		StompSessionHandler unvalidatedHandler = new TestAbstractStompSessionHandler(unvalidatedFailure) {
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
							assertThat(message.getName()).isEqualTo(UNVALIDATED_ROOM);
							assertThat(message.getOwner()).isEqualTo(UNVALIDATED);
							assertThat(message.getParticipants()).hasSize(1).contains(USER);
						} catch (Throwable t) {
							userFailure.set(t);
						} finally {
							latch.countDown();
						}
					}
				});
			}
		};

		connect(userHandler, USER);
		connect(unvalidatedHandler, UNVALIDATED);
		connect(adminHandler, ADMIN);

		if (!latch.await(25, TimeUnit.SECONDS)) {
			fail("timeout");
		}
		if (adminFailure.get() != null) {
			throw new AssertionError("", adminFailure.get());
		}
		if (userFailure.get() != null) {
			throw new AssertionError("", adminFailure.get());
		}
		if (unvalidatedFailure.get() != null) {
			throw new AssertionError("", adminFailure.get());
		}

		List<RoomEntity> savedRooms = roomJpaRepository.findAll();
		assertThat(savedRooms).hasSize(2);
		Set<RoomEntity> unvalidatedRooms = roomJpaRepository.findByOwner(UNVALIDATED);
		assertThat(unvalidatedRooms).hasSize(1);
		RoomEntity foundUnvalidatedRoom = unvalidatedRooms.iterator().next();
		assertThat(foundUnvalidatedRoom.getName()).isEqualTo(UNVALIDATED_ROOM);
		assertThat(foundUnvalidatedRoom.getOwner()).isEqualTo(UNVALIDATED);
		assertThat(foundUnvalidatedRoom.getParticipants()).hasSize(1).contains(USER);
		assertThat(foundUnvalidatedRoom.isActive()).isTrue();
		Set<RoomEntity> foundAdminRooms = roomJpaRepository.findByOwner(ADMIN);
		assertThat(foundAdminRooms).hasSize(1);
		RoomEntity foundAdminRoom = foundAdminRooms.iterator().next();
		assertThat(foundAdminRoom.getName()).isEqualTo(ADMIN_ROOM);
		assertThat(foundAdminRoom.getOwner()).isEqualTo(ADMIN);
		assertThat(foundAdminRoom.isInactive()).isTrue();
	}

}
