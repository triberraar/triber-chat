package be.tribersoft.triber.chat.room.domain.impl;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class RoomEntityAddParticipantTest {

	private static final String PARTICIPANT = "participant";

	@Test
	public void addsParticipant() {
		RoomEntity roomEntity = new RoomEntity("owner", "name");

		roomEntity.addParticipant(PARTICIPANT);

		assertThat(roomEntity.getParticipants()).hasSize(1).contains(PARTICIPANT);
	}
}
