package be.tribersoft.triber.chat.room.domain.api;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

public interface RoomMessage {

	@NotEmpty(message = "room.validation.name.empty")
	@Length(max = 256, min = 4, message = "room.validation.name.length")
	String getName();

	@NotEmpty(message = "room.validation.owner.empty")
	@Length(max = 256, min = 1, message = "room.validation.owner.length")
	String getOwner();
}
