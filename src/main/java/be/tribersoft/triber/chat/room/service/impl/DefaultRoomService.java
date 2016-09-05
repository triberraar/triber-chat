package be.tribersoft.triber.chat.room.service.impl;

import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;

import be.tribersoft.triber.chat.room.domain.api.Room;
import be.tribersoft.triber.chat.room.domain.api.RoomFacade;
import be.tribersoft.triber.chat.room.service.ap.RoomService;

@Named
@Transactional
public class DefaultRoomService implements RoomService {

	@Inject
	private RoomFacade roomFacade;

	@Override
	public Room create(String owner, String name) {
		return roomFacade.create(owner, name);
	}
}
