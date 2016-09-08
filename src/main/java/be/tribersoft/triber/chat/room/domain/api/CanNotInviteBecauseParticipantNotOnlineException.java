package be.tribersoft.triber.chat.room.domain.api;

import be.tribersoft.triber.chat.common.exception.BusinessException;

public class CanNotInviteBecauseParticipantNotOnlineException extends BusinessException {
	public CanNotInviteBecauseParticipantNotOnlineException() {
		super("can.not.invite.because.participant.not.online");
	}
}
