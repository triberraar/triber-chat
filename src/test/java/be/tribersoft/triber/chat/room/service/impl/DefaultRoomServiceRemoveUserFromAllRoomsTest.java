package be.tribersoft.triber.chat.room.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;

import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.google.common.collect.Sets;

import be.tribersoft.triber.chat.room.domain.api.Room;
import be.tribersoft.triber.chat.room.domain.api.RoomFacade;

@RunWith(MockitoJUnitRunner.class)
public class DefaultRoomServiceRemoveUserFromAllRoomsTest {

	private static final String USER = "user";

	@InjectMocks
	private DefaultRoomService defaultRoomService;

	@Mock
	private RoomFacade roomFacade;

	@Mock
	private Room room;

	@Before
	public void setUp() {
		doReturn(Sets.newHashSet(room)).when(roomFacade).removeUserFromAllRooms(USER);
	}

	@Test
	public void delegatesToFacade() {
		Set<? extends Room> result = defaultRoomService.removeUserFromAllRooms(USER);

		assertThat(result).hasSize(1);
		Room resultingRoom = result.iterator().next();
		assertThat(resultingRoom).isEqualTo(room);
	}
}
