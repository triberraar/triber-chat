package be.tribersoft.triber.chat.room.domain.api;

public interface RoomFacade {

	Room create(String owner, String name);

	Room invite(String roomId, String participant);

}
