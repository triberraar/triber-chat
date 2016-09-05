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
public class DefaultRoomFacadeCreateTest {

	private static final String NAME = "name";
	private static final String OWNER = "owner";

	@InjectMocks
	private DefaultRoomFacade defaultRoomFacade;

	@Mock
	private DefaultRoomRepository defaultRoomRepository;

	@Mock
	private RoomFactory roomFactory;

	@Mock
	private RoomEntity roomEntity;

	@Before
	public void setUp() {
		when(roomFactory.create(OWNER, NAME)).thenReturn(roomEntity);
	}

	@Test
	public void createsAndSavesRoom() {
		RoomEntity createdRoomEntity = defaultRoomFacade.create(OWNER, NAME);

		verify(defaultRoomRepository).save(roomEntity);
		assertThat(createdRoomEntity).isSameAs(roomEntity);
	}
}
