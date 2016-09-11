package be.tribersoft.triber.chat.room.controller;

import java.security.Principal;

import javax.inject.Inject;
import javax.validation.Valid;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

import be.tribersoft.triber.chat.common.WebSocketService;
import be.tribersoft.triber.chat.room.domain.api.Room;
import be.tribersoft.triber.chat.room.service.api.RoomService;

@Controller
public class RoomController {

	@Inject
	private RoomService roomService;

	@Inject
	private RoomValidator roomValidator;

	@Inject
	private WebSocketService webSocketService;

	@MessageMapping("/room/create")
	public void create(@Valid RoomFromJsonAdapter roomFromJsonAdapter, Principal principal) {
		Room room = roomService.create(principal.getName(), roomFromJsonAdapter.getName());

		sendToEveryoneInRoom(room);
	}

	@MessageMapping("/room/invite")
	public void invite(RoomInvitationFromJsonAdapter roomInvitationFromJsonAdapter, Principal principal) {
		roomValidator.validateInvitation(roomInvitationFromJsonAdapter, principal);

		Room room = roomService.invite(roomInvitationFromJsonAdapter.getId(), roomInvitationFromJsonAdapter.getParticipant());

		sendToEveryoneInRoom(room);
	}

	private void sendToEveryoneInRoom(Room room) {
		webSocketService.sendToUser(room.getOwner(), "/topic/room/status", new RoomToJsonAdapter(room));
		room.getParticipants().forEach((participant) -> webSocketService.sendToUser(participant, "/topic/room/status", new RoomToJsonAdapter(room)));
	}
}
