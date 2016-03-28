package be.tribersoft.triber.chat.common.exception;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import be.tribersoft.triber.chat.user.domain.api.CanNotActivateUserException;

public class CanNotActivateUserExceptionHandlerProcessTest {

	private CanNotActivateUserExceptionHandler handler = new CanNotActivateUserExceptionHandler();

	@Test
	public void wrapsException() {
		CanNotActivateUserException exception = new CanNotActivateUserException();

		ErrorToJsonAdapter result = handler.process(exception);

		assertThat(result.getErrorCode()).isEqualTo("user.can.not.activate");
	}

	@Test
	public void resultsInBadRequest() throws NoSuchMethodException, SecurityException {
		ResponseStatus result = AnnotationUtils.findAnnotation(CanNotActivateUserExceptionHandler.class.getMethod("process", CanNotActivateUserException.class), ResponseStatus.class);

		assertThat(result).isNotNull();
		assertThat(result.code()).isEqualTo(HttpStatus.BAD_REQUEST);
	}
}
