package be.tribersoft.triber.chat.room.domain.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import be.tribersoft.triber.chat.room.domain.api.RoomNotFoundException;

@RunWith(MockitoJUnitRunner.class)
public class DefaultRoomRepositoryGetByIdTest {

	private static final String ID = "id";

	@InjectMocks
	private DefaultRoomRepository defaultRoomRepository;

	@Mock
	private RoomJpaRepository roomJpaRepository;

	@Mock
	private RoomEntity roomEntity;

	@Before
	public void setUp() {
	}

	@Test(expected = RoomNotFoundException.class)
	public void failsWhenRoomNotFound() {
		when(roomJpaRepository.findOne(ID)).thenReturn(null);

		defaultRoomRepository.getById(ID);
	}

	@Test
	public void returnsRoomWhenFound() {
		when(roomJpaRepository.findOne(ID)).thenReturn(roomEntity);

		assertThat(defaultRoomRepository.getById(ID)).isEqualTo(roomEntity);
	}
}
