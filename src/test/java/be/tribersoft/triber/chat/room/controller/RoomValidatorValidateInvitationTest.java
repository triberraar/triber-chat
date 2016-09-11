package be.tribersoft.triber.chat.room.controller;

import static org.mockito.Mockito.when;

import java.security.Principal;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import be.tribersoft.triber.chat.room.domain.api.CanNotInviteBecauseNotOwnerException;
import be.tribersoft.triber.chat.room.domain.api.CanNotInviteBecauseParticipantNotOnlineException;
import be.tribersoft.triber.chat.room.domain.api.CanNotInviteOwnerException;
import be.tribersoft.triber.chat.room.domain.api.Room;
import be.tribersoft.triber.chat.room.domain.api.RoomRepository;
import be.tribersoft.triber.chat.user.domain.api.ConnectedUsersRepository;

@RunWith(MockitoJUnitRunner.class)
public class RoomValidatorValidateInvitationTest {

	private static final String OTHER_OWNER = "other owner";
	private static final String PARTICIPANT = "participant";
	private static final String OWNER = "owner";
	private static final String ID = "id";

	@InjectMocks
	private RoomValidator roomValidator;

	@Mock
	private RoomRepository roomRepository;

	@Mock
	private ConnectedUsersRepository connectedUsersRepository;

	@Mock
	private RoomInvitationFromJsonAdapter roomInvitationFromJsonAdapter;

	@Mock
	private Principal principal;

	@Mock
	private Room room;

	@Before
	public void setUp() {
		when(roomInvitationFromJsonAdapter.getId()).thenReturn(ID);
		when(roomRepository.getById(ID)).thenReturn(room);
		when(room.getOwner()).thenReturn(OWNER);
		when(roomInvitationFromJsonAdapter.getParticipant()).thenReturn(PARTICIPANT);
		when(connectedUsersRepository.exists(PARTICIPANT)).thenReturn(true);
		when(connectedUsersRepository.exists(OWNER)).thenReturn(true);
		when(principal.getName()).thenReturn(OWNER);
	}

	@Test(expected = CanNotInviteBecauseNotOwnerException.class)
	public void failsWhenRoomNotOwnedByUser() {
		when(room.getOwner()).thenReturn(OTHER_OWNER);

		roomValidator.validateInvitation(roomInvitationFromJsonAdapter, principal);
	}

	@Test(expected = CanNotInviteBecauseParticipantNotOnlineException.class)
	public void failsWhenParticipantIsNotOnline() {
		when(connectedUsersRepository.exists(PARTICIPANT)).thenReturn(false);

		roomValidator.validateInvitation(roomInvitationFromJsonAdapter, principal);
	}

	@Test(expected = CanNotInviteOwnerException.class)
	public void failsWhenInvitingOwner() {
		when(roomInvitationFromJsonAdapter.getParticipant()).thenReturn(OWNER);

		roomValidator.validateInvitation(roomInvitationFromJsonAdapter, principal);
	}
}
