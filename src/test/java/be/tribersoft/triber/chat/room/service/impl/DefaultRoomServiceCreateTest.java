package be.tribersoft.triber.chat.room.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
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
public class DefaultRoomServiceCreateTest {

	private static final String OWNER = "owner";
	private static final String NAME = "name";

	@InjectMocks
	private DefaultRoomService defaultRoomService;

	@Mock
	private RoomFacade roomFacade;

	@Mock
	private Room room;

	@Before
	public void setUp() {
		when(roomFacade.create(OWNER, NAME)).thenReturn(room);
	}

	@Test
	public void delgatesToFacade() {
		Room result = defaultRoomService.create(OWNER, NAME);

		assertThat(result).isSameAs(room);
	}
}
