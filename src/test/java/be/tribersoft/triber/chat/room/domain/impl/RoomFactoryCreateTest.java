package be.tribersoft.triber.chat.room.domain.impl;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class RoomFactoryCreateTest {

	private static final String OWNER = "owner";
	private static final String NAME = "name";

	private RoomFactory roomFactory = new RoomFactory();

	@Test
	public void createsARoomEntity() {
		RoomEntity roomEntity = roomFactory.create(OWNER, NAME);

		assertThat(roomEntity.getOwner()).isEqualTo(OWNER);
		assertThat(roomEntity.getName()).isEqualTo(NAME);
		assertThat(roomEntity.getParticipants()).isEmpty();
	}
}
