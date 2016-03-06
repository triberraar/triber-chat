package be.tribersoft.triber.chat.controller.common;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import be.tribersoft.triber.chat.controller.common.exception.ValidationException;

@ControllerAdvice
public class ValidationExceptionHandler {

	@ExceptionHandler(ValidationException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public ValidationErrorToJsonAdapter process(ValidationException ex) {
		return new ValidationErrorToJsonAdapter(ex.getErrorCode());
	}

}
