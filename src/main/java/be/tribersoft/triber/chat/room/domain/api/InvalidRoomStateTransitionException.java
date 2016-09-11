package be.tribersoft.triber.chat.room.domain.api;

import be.tribersoft.triber.chat.common.exception.BusinessException;

public class InvalidRoomStateTransitionException extends BusinessException {
	public InvalidRoomStateTransitionException() {
		super("can.not.transition");
	}
}
