package be.tribersoft.triber.chat.room.controller;

import java.util.Set;

import be.tribersoft.triber.chat.room.domain.api.Room;

public class RoomToJsonAdapter {

	private String name;
	private String id;
	private Set<String> participants;

	protected RoomToJsonAdapter() {
	}

	public RoomToJsonAdapter(Room room) {
		this.name = room.getName();
		this.id = room.getId();
		this.participants = room.getParticipants();
	}

	public String getName() {
		return name;
	}

	public String getId() {
		return id;
	}

	public Set<String> getParticipants() {
		return participants;
	}
}
