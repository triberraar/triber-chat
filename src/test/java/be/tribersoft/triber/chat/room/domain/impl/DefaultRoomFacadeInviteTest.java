package be.tribersoft.triber.chat.room.domain.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class DefaultRoomFacadeInviteTest {

	private static final String PARTICPANT = "particpant";
	private static final String ROOM_ID = "roomId";

	@InjectMocks
	private DefaultRoomFacade defaultRoomFacade;

	@Mock
	private DefaultRoomRepository defaultRoomRepository;
	@Mock
	private RoomEntity room;

	@Before
	public void setUp() {
		when(defaultRoomRepository.getById(ROOM_ID)).thenReturn(room);
	}

	@Test
	public void addsParticipant() {
		RoomEntity invitedRoom = defaultRoomFacade.invite(ROOM_ID, PARTICPANT);

		verify(room).addParticipant(PARTICPANT);
		assertThat(invitedRoom).isEqualTo(room);
	}
}
