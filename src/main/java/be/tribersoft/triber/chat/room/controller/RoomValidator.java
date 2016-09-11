package be.tribersoft.triber.chat.room.controller;

import java.security.Principal;

import javax.inject.Inject;
import javax.inject.Named;

import be.tribersoft.triber.chat.room.domain.api.CanNotInviteBecauseNotOwnerException;
import be.tribersoft.triber.chat.room.domain.api.CanNotInviteBecauseParticipantNotOnlineException;
import be.tribersoft.triber.chat.room.domain.api.CanNotInviteOwnerException;
import be.tribersoft.triber.chat.room.domain.api.Room;
import be.tribersoft.triber.chat.room.domain.api.RoomRepository;
import be.tribersoft.triber.chat.user.domain.api.ConnectedUsersRepository;

@Named
public class RoomValidator {

	@Inject
	private RoomRepository roomRepository;

	@Inject
	private ConnectedUsersRepository connectedUsersRepository;

	public void validateInvitation(RoomInvitationFromJsonAdapter roomInvitationFromJsonAdapter, Principal principal) {
		Room room = roomRepository.getById(roomInvitationFromJsonAdapter.getId());

		if (!room.getOwner().equals(principal.getName())) {
			throw new CanNotInviteBecauseNotOwnerException();
		}

		if (!connectedUsersRepository.exists(roomInvitationFromJsonAdapter.getParticipant())) {
			throw new CanNotInviteBecauseParticipantNotOnlineException();
		}

		if (room.getOwner().equals(roomInvitationFromJsonAdapter.getParticipant())) {
			throw new CanNotInviteOwnerException();
		}
	}

}
