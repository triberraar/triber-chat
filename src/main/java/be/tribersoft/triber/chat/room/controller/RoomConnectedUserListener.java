package be.tribersoft.triber.chat.room.controller;

import java.util.Set;
import java.util.function.Predicate;

import javax.inject.Inject;
import javax.inject.Named;

import be.tribersoft.triber.chat.common.WebSocketService;
import be.tribersoft.triber.chat.room.domain.api.Room;
import be.tribersoft.triber.chat.room.service.api.RoomService;
import be.tribersoft.triber.chat.user.domain.api.User;
import be.tribersoft.triber.chat.user.service.api.ConnectedUserListener;

@Named
public class RoomConnectedUserListener implements ConnectedUserListener {

	@Inject
	private RoomService roomService;
	@Inject
	private WebSocketService webSocketService;

	@Override
	public void userConnected(User user) {
	}

	@Override
	public void userDisconnected(User user) {
		String userName = user.getUsername();
		Set<? extends Room> affectedRooms = roomService.removeUserFromAllRooms(userName);

		affectedRooms.stream().filter(findOwnedRooms(userName)).forEach(room -> room.getParticipants().forEach(participant -> notify("/topic/room/deleted", room, participant)));

		affectedRooms.stream().filter(findParticipatedRooms(userName)).forEach((room) -> {
			room.getParticipants().forEach(participant -> notify("/topic/room/status", room, participant));
			notify("/topic/room/status", room, room.getOwner());
		});
	}

	private void notify(String channel, Room room, String user) {
		webSocketService.sendToUser(user, channel, new RoomToJsonAdapter(room));
	}

	private Predicate<Room> findOwnedRooms(String user) {
		return room -> room.getOwner().equals(user);
	}

	private Predicate<Room> findParticipatedRooms(String user) {
		return room -> !room.getOwner().equals(user);
	}

}
