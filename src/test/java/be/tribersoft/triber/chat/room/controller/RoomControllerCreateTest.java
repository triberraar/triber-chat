package be.tribersoft.triber.chat.room.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.security.Principal;
import java.util.HashSet;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import be.tribersoft.triber.chat.common.WebSocketService;
import be.tribersoft.triber.chat.room.domain.api.Room;
import be.tribersoft.triber.chat.room.service.api.RoomService;

@RunWith(MockitoJUnitRunner.class)
public class RoomControllerCreateTest {

	private static final String ID = "id";
	private static final String NAME = "name";
	private static final String USERNAME = "username";

	@InjectMocks
	private RoomController roomController;

	@Mock
	private RoomService roomService;

	@Mock
	private WebSocketService webSocketService;

	@Mock
	private RoomFromJsonAdapter roomFromJsonAdapter;

	@Mock
	private Principal principal;

	@Mock
	private Room room;

	@Captor
	private ArgumentCaptor<RoomToJsonAdapter> jsonCaptor;

	@Before
	public void setUp() {
		when(principal.getName()).thenReturn(USERNAME);
		when(roomFromJsonAdapter.getName()).thenReturn(NAME);
		when(roomService.create(USERNAME, NAME)).thenReturn(room);
		when(room.getId()).thenReturn(ID);
		when(room.getName()).thenReturn(NAME);
		when(room.getParticipants()).thenReturn(new HashSet<>());
		when(room.getOwner()).thenReturn(USERNAME);
	}

	@Test
	public void shouldCreateRoomAndAnswer() {
		roomController.create(roomFromJsonAdapter, principal);

		verify(webSocketService).sendToUser(eq(USERNAME), eq("/topic/room/status"), jsonCaptor.capture());
		assertThat(jsonCaptor.getValue().getId()).isEqualTo(ID);
		assertThat(jsonCaptor.getValue().getName()).isEqualTo(NAME);
		assertThat(jsonCaptor.getValue().getParticipants()).isEmpty();
	}

}
