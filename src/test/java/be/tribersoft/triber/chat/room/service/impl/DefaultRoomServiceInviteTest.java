package be.tribersoft.triber.chat.room.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import be.tribersoft.triber.chat.room.domain.api.Room;
import be.tribersoft.triber.chat.room.domain.api.RoomFacade;

@RunWith(MockitoJUnitRunner.class)
public class DefaultRoomServiceInviteTest {

	private static final String PARTICIPANT = "participant";

	private static final String ROOM_ID = "roomId";

	@InjectMocks
	private DefaultRoomService defaultRoomService;

	@Mock
	private RoomFacade roomFacade;

	@Mock
	private Room room;

	@Before
	public void setUp() {
		when(roomFacade.invite(ROOM_ID, PARTICIPANT)).thenReturn(room);
	}

	@Test
	public void delgatesToFacade() {
		Room invitedRoom = defaultRoomService.invite(ROOM_ID, PARTICIPANT);

		verify(roomFacade).invite(ROOM_ID, PARTICIPANT);
		assertThat(invitedRoom).isEqualTo(room);
	}
}
