package be.tribersoft.triber.chat.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import be.tribersoft.triber.chat.room.domain.api.CanNotInviteBecauseNotOwnerException;
import be.tribersoft.triber.chat.room.domain.api.CanNotInviteBecauseParticipantNotOnlineException;

@ControllerAdvice
public class CanNotInviteExceptionHandler {
	@ExceptionHandler(CanNotInviteBecauseParticipantNotOnlineException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public ErrorToJsonAdapter process(CanNotInviteBecauseParticipantNotOnlineException ex) {
		return new ErrorToJsonAdapter(ex.getErrorCode());
	}

	@ExceptionHandler(CanNotInviteBecauseNotOwnerException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public ErrorToJsonAdapter process(CanNotInviteBecauseNotOwnerException ex) {
		return new ErrorToJsonAdapter(ex.getErrorCode());
	}
}
