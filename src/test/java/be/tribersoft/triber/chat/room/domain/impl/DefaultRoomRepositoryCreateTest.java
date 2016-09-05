package be.tribersoft.triber.chat.room.domain.impl;

import static org.mockito.Mockito.verify;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class DefaultRoomRepositoryCreateTest {

	@InjectMocks
	private DefaultRoomRepository defaultRoomRepository;

	@Mock
	private RoomJpaRepository roomJpaRepository;

	@Mock
	private RoomEntity roomEntity;

	@Test
	public void delegatesToJpaRepository() {
		defaultRoomRepository.save(roomEntity);

		verify(roomJpaRepository).save(roomEntity);
	}
}
