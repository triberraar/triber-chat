package be.tribersoft.triber.chat.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import be.tribersoft.triber.chat.user.domain.api.CanNotActivateUserException;

@ControllerAdvice
public class CanNotActivateUserExceptionHandler {
	@ExceptionHandler(CanNotActivateUserException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public ErrorToJsonAdapter process(CanNotActivateUserException ex) {
		return new ErrorToJsonAdapter(ex.getErrorCode());
	}
}
