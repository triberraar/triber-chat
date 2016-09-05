package be.tribersoft.triber.chat.room.domain.api;

import java.util.Set;

public interface Room extends RoomMessage {

	Set<String> getParticipants();

}
