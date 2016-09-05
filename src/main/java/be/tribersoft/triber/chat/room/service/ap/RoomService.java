package be.tribersoft.triber.chat.room.service.ap;

import be.tribersoft.triber.chat.room.domain.api.Room;

public interface RoomService {

	Room create(String owner, String name);

}
