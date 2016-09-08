package be.tribersoft.triber.chat.room.domain.api;

import be.tribersoft.triber.chat.common.exception.NotFoundException;

public class RoomNotFoundException extends NotFoundException {

	public RoomNotFoundException() {
		super("room.not.found.exception");
	}
}
