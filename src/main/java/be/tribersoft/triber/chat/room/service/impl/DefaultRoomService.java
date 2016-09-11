package be.tribersoft.triber.chat.room.service.impl;

import java.util.Set;

import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;

import be.tribersoft.triber.chat.room.domain.api.Room;
import be.tribersoft.triber.chat.room.domain.api.RoomFacade;
import be.tribersoft.triber.chat.room.service.api.RoomService;

@Named
@Transactional
public class DefaultRoomService implements RoomService {

	@Inject
	private RoomFacade roomFacade;

	@Override
	public Room create(String owner, String name) {
		return roomFacade.create(owner, name);
	}

	@Override
	public Room invite(String roomId, String participant) {
		return roomFacade.invite(roomId, participant);
	}

	@Override
	public Set<? extends Room> removeUserFromAllRooms(String username) {
		return roomFacade.removeUserFromAllRooms(username);
	}
}
