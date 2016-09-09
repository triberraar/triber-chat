package be.tribersoft.triber.chat.room.controller;

import java.security.Principal;

import javax.inject.Inject;
import javax.validation.Valid;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import be.tribersoft.triber.chat.room.domain.api.Room;
import be.tribersoft.triber.chat.room.service.api.RoomService;

@Controller
public class RoomController {

	@Inject
	private RoomService roomService;

	@Inject
	private RoomValidator roomValidator;

	@Inject
	private SimpMessagingTemplate messagingTemplate;

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
		messagingTemplate.convertAndSendToUser(room.getOwner(), "/topic/room/status", new RoomToJsonAdapter(room));
		room.getParticipants().stream().forEach((participant) -> messagingTemplate.convertAndSendToUser(participant, "/topic/room/status", new RoomToJsonAdapter(room)));
	}
}
