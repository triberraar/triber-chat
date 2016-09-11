package be.tribersoft.triber.chat.room.domain.api;

import be.tribersoft.triber.chat.common.exception.BusinessException;

public class CanNotInviteOwnerException extends BusinessException {

	public CanNotInviteOwnerException() {
		super("can.not.invite.owner");
	}

}
