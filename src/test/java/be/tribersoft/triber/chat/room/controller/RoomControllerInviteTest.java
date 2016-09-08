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
import org.springframework.messaging.simp.SimpMessagingTemplate;

import be.tribersoft.triber.chat.room.domain.api.Room;
import be.tribersoft.triber.chat.room.service.api.RoomService;

@RunWith(MockitoJUnitRunner.class)
public class RoomControllerInviteTest {

	private static final String PARTICIPANT = "participant";
	private static final String ID = "id";
	private static final String NAME = "name";
	private static final String USERNAME = "username";

	@InjectMocks
	private RoomController roomController;

	@Mock
	private RoomService roomService;

	@Mock
	private SimpMessagingTemplate messagingTemplate;

	@Mock
	private RoomValidator roomValidator;

	@Mock
	private RoomInvitationFromJsonAdapter roomInvitationFromJsonAdapter;

	@Mock
	private Principal principal;

	@Mock
	private Room room;

	@Captor
	private ArgumentCaptor<RoomToJsonAdapter> jsonCaptor;

	@Before
	public void setUp() {
		when(roomService.create(USERNAME, NAME)).thenReturn(room);
		when(roomInvitationFromJsonAdapter.getId()).thenReturn(ID);
		when(roomInvitationFromJsonAdapter.getParticipant()).thenReturn(PARTICIPANT);
		when(roomService.invite(ID, PARTICIPANT)).thenReturn(room);
		when(room.getId()).thenReturn(ID);
		when(room.getName()).thenReturn(NAME);
		when(room.getParticipants()).thenReturn(new HashSet<>());
	}

	@Test
	public void shouldInviteAParticipant() {
		roomController.invite(roomInvitationFromJsonAdapter, principal);

		verify(roomValidator).validateInvitation(roomInvitationFromJsonAdapter, principal);
		verify(messagingTemplate).convertAndSendToUser(eq(PARTICIPANT), eq("/topic/room/invitation"), jsonCaptor.capture());
		assertThat(jsonCaptor.getValue().getId()).isEqualTo(ID);
		assertThat(jsonCaptor.getValue().getName()).isEqualTo(NAME);
		assertThat(jsonCaptor.getValue().getParticipants()).isEmpty();
	}

}
