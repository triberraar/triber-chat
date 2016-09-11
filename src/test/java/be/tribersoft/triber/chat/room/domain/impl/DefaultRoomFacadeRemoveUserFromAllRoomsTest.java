package be.tribersoft.triber.chat.room.domain.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.google.common.collect.Sets;

@RunWith(MockitoJUnitRunner.class)
public class DefaultRoomFacadeRemoveUserFromAllRoomsTest {

	private static final String USER = "user";

	@InjectMocks
	private DefaultRoomFacade defaultRoomFacade;

	@Mock
	private DefaultRoomRepository defaultRoomRepository;

	@Mock
	private RoomEntity ownerRoom1, ownerRoom2, participantRoom1, participantRoom2;

	@Before
	public void setUp() {
		when(defaultRoomRepository.findByOwner(USER)).thenReturn(Sets.newHashSet(ownerRoom1, ownerRoom2));
		when(defaultRoomRepository.findByParticipant(USER)).thenReturn(Sets.newHashSet(participantRoom1, participantRoom2));
	}

	@Test
	public void deletesRoomsWhereUserIsOwnerAndRemovesUserFromParticipantsInOtherRooms() {
		Set<RoomEntity> result = defaultRoomFacade.removeUserFromAllRooms(USER);

		verify(defaultRoomRepository).delete(Sets.newHashSet(ownerRoom1, ownerRoom2));
		verify(participantRoom1).removeParticipant(USER);
		verify(participantRoom2).removeParticipant(USER);
		assertThat(result).hasSize(4).contains(ownerRoom1, ownerRoom2, participantRoom1, participantRoom2);
	}

}
