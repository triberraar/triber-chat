package be.tribersoft.triber.chat.room.domain.api;

import java.util.Set;

public interface RoomRepository {

	Room getById(String id);

	Set<? extends Room> findByParticipant(String participant);

	Set<? extends Room> findByOwner(String owner);

}
