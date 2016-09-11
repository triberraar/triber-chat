package be.tribersoft.triber.chat.room.domain.api;

import java.util.Set;

public interface RoomFacade {

	Room create(String owner, String name);

	Room invite(String roomId, String participant);

	Set<? extends Room> removeUserFromAllRooms(String username);

}
