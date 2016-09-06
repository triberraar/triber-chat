package be.tribersoft.triber.chat.room.service.api;

import be.tribersoft.triber.chat.room.domain.api.Room;

public interface RoomService {

	Room create(String owner, String name);

}
