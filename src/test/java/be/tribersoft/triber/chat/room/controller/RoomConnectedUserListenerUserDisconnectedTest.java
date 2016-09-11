package be.tribersoft.triber.chat.room.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.google.common.collect.Sets;

import be.tribersoft.triber.chat.common.WebSocketService;
import be.tribersoft.triber.chat.room.domain.api.Room;
import be.tribersoft.triber.chat.room.service.api.RoomService;
import be.tribersoft.triber.chat.user.domain.api.User;

@RunWith(MockitoJUnitRunner.class)
public class RoomConnectedUserListenerUserDisconnectedTest {

	private static final String PARTICIPATED_ID = "participated id";
	private static final String PARTICIPATED_ROOM = "participated room";
	private static final String OWNED_ID = "owned id";
	private static final String OWNED_ROOM = "owned room";
	private static final String USER = "user";
	private static final String OTHER = "other";
	private static final String OTHER_OWNER = "other owner";

	@InjectMocks
	private RoomConnectedUserListener roomConnectedUserListener;

	@Mock
	private User user;
	@Mock
	private RoomService roomService;
	@Mock
	private WebSocketService webSocketService;
	@Mock
	private Room ownedRoom, participatedRoom;
	@Captor
	private ArgumentCaptor<RoomToJsonAdapter> roomToJsonAdaptorCaptor;

	@Before
	public void setUp() {
		when(user.getUsername()).thenReturn(USER);
		doReturn(Sets.newHashSet(ownedRoom, participatedRoom)).when(roomService).removeUserFromAllRooms(USER);
		when(ownedRoom.getOwner()).thenReturn(USER);
		when(ownedRoom.getParticipants()).thenReturn(Sets.newHashSet(OTHER));
		when(ownedRoom.getName()).thenReturn(OWNED_ROOM);
		when(ownedRoom.getId()).thenReturn(OWNED_ID);
		when(participatedRoom.getOwner()).thenReturn(OTHER_OWNER);
		when(participatedRoom.getParticipants()).thenReturn(Sets.newHashSet(OTHER));
		when(participatedRoom.getName()).thenReturn(PARTICIPATED_ROOM);
		when(participatedRoom.getId()).thenReturn(PARTICIPATED_ID);
	}

	@Test
	public void removesUserFromRoomAndNotifies() {
		roomConnectedUserListener.userDisconnected(user);

		verify(webSocketService).sendToUser(eq(OTHER), eq("/topic/room/deleted"), roomToJsonAdaptorCaptor.capture());
		assertThat(roomToJsonAdaptorCaptor.getValue().getId()).isEqualTo(OWNED_ID);
		assertThat(roomToJsonAdaptorCaptor.getValue().getOwner()).isEqualTo(USER);
		assertThat(roomToJsonAdaptorCaptor.getValue().getParticipants()).hasSize(1).contains(OTHER);
		assertThat(roomToJsonAdaptorCaptor.getValue().getName()).isEqualTo(OWNED_ROOM);

		verify(webSocketService).sendToUser(eq(OTHER), eq("/topic/room/status"), roomToJsonAdaptorCaptor.capture());
		assertThat(roomToJsonAdaptorCaptor.getValue().getId()).isEqualTo(PARTICIPATED_ID);
		assertThat(roomToJsonAdaptorCaptor.getValue().getOwner()).isEqualTo(OTHER_OWNER);
		assertThat(roomToJsonAdaptorCaptor.getValue().getName()).isEqualTo(PARTICIPATED_ROOM);
		assertThat(roomToJsonAdaptorCaptor.getValue().getParticipants()).hasSize(1).contains(OTHER);
		verify(webSocketService).sendToUser(eq(OTHER_OWNER), eq("/topic/room/status"), roomToJsonAdaptorCaptor.capture());
		assertThat(roomToJsonAdaptorCaptor.getValue().getId()).isEqualTo(PARTICIPATED_ID);
		assertThat(roomToJsonAdaptorCaptor.getValue().getOwner()).isEqualTo(OTHER_OWNER);
		assertThat(roomToJsonAdaptorCaptor.getValue().getName()).isEqualTo(PARTICIPATED_ROOM);
		assertThat(roomToJsonAdaptorCaptor.getValue().getParticipants()).hasSize(1).contains(OTHER);
	}
}
