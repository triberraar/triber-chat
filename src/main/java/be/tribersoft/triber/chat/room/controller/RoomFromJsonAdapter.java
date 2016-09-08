package be.tribersoft.triber.chat.room.controller;

import be.tribersoft.triber.chat.room.domain.api.RoomMessage;

public class RoomFromJsonAdapter implements RoomMessage {

	private String name;

	@Override
	public String getName() {
		return name;
	}

}
