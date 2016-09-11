package be.tribersoft.triber.chat.room.domain.impl;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.mockito.internal.util.reflection.Whitebox;

import be.tribersoft.triber.chat.room.domain.api.InvalidRoomStateTransitionException;

public class RoomEntityInactivateTest {

	@Test
	public void succeedsWhenRoomIsActive() {
		RoomEntity roomEntity = new RoomEntity("owner", "name");

		roomEntity.inactivate();

		assertThat(roomEntity.isInactive()).isTrue();
	}

	@Test(expected = InvalidRoomStateTransitionException.class)
	public void failsWhenRoomIsInactive() {
		RoomEntity roomEntity = new RoomEntity("owner", "name");
		Whitebox.setInternalState(roomEntity, "currentState", RoomState.INACTIVE);

		roomEntity.inactivate();
	}
}
