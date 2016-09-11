package be.tribersoft.triber.chat.room.domain.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.google.common.collect.Sets;

@RunWith(MockitoJUnitRunner.class)
public class DefaultRoomRepositoryFindByOwnerTest {

	@InjectMocks
	private DefaultRoomRepository defaultRoomRepository;

	@Mock
	private RoomJpaRepository roomJpaRepository;

	@Mock
	private RoomEntity roomEntity1, roomEntity2;

	@Before
	public void setUp() {
		when(roomJpaRepository.findByOwner("owner")).thenReturn(Sets.newHashSet(roomEntity1, roomEntity2));
	}

	@Test
	public void delegatesToJpaRepository() {
		assertThat(defaultRoomRepository.findByOwner("owner")).isEqualTo(Sets.newHashSet(roomEntity1, roomEntity2));
	}
}
