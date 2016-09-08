package be.tribersoft.triber.chat.room.domain.api;

import java.util.Set;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

public interface Room extends RoomMessage {

	Set<String> getParticipants();

	@NotEmpty(message = "room.validation.owner.empty")
	@Length(max = 256, min = 1, message = "room.validation.owner.length")
	String getOwner();

	String getId();
}
