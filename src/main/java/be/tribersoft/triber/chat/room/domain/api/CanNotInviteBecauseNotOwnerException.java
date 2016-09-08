package be.tribersoft.triber.chat.room.domain.api;

import be.tribersoft.triber.chat.common.exception.BusinessException;

public class CanNotInviteBecauseNotOwnerException extends BusinessException {

	public CanNotInviteBecauseNotOwnerException() {
		super("can.not.invite.because.not.owner");
	}

}
