package be.tribersoft.triber.chat.room.domain.impl;

import javax.inject.Named;

@Named
public class RoomFactory {

	public RoomEntity create(String owner, String name) {
		return new RoomEntity(owner, name);
	}
}
