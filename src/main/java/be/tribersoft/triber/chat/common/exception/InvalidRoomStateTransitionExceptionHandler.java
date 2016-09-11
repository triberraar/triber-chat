package be.tribersoft.triber.chat.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import be.tribersoft.triber.chat.room.domain.api.InvalidRoomStateTransitionException;

@ControllerAdvice
public class InvalidRoomStateTransitionExceptionHandler {

	@ExceptionHandler(InvalidRoomStateTransitionException.class)
	@ResponseStatus(HttpStatus.CONFLICT)
	@ResponseBody
	public ErrorToJsonAdapter process(InvalidRoomStateTransitionException ex) {
		return new ErrorToJsonAdapter(ex.getErrorCode());
	}
}
