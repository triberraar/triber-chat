package be.tribersoft.triber.chat.room.service.api;

import java.util.Set;

import be.tribersoft.triber.chat.room.domain.api.Room;

public interface RoomService {

	Room create(String owner, String name);

	Room invite(String roomId, String participant);

	Set<? extends Room> removeUserFromAllRooms(String username);

}
