package be.tribersoft.triber.chat.room.domain.impl;

import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.google.common.collect.Sets;

@RunWith(MockitoJUnitRunner.class)
public class DefaultRoomRepositoryDeleteTest {

	@InjectMocks
	private DefaultRoomRepository defaultRoomRepository;

	@Mock
	private RoomJpaRepository roomJpaRepository;

	@Mock
	private RoomEntity roomEntity1, roomEntity2;

	@Before
	public void setUp() {
	}

	@Test
	public void delegatesToJpaRepository() {
		defaultRoomRepository.delete(Sets.newHashSet(roomEntity1, roomEntity2));

		verify(roomJpaRepository).delete(Sets.newHashSet(roomEntity1, roomEntity2));
	}
}
