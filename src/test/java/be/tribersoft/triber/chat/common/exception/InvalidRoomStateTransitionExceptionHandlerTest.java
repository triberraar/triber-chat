package be.tribersoft.triber.chat.common.exception;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import be.tribersoft.triber.chat.room.domain.api.InvalidRoomStateTransitionException;

public class InvalidRoomStateTransitionExceptionHandlerTest {

	private InvalidRoomStateTransitionExceptionHandler handler = new InvalidRoomStateTransitionExceptionHandler();

	@Test
	public void wrapsInvalidRoomStateTransitionException() {
		InvalidRoomStateTransitionException exception = new InvalidRoomStateTransitionException();

		ErrorToJsonAdapter result = handler.process(exception);

		assertThat(result.getErrorCode()).isEqualTo("can.not.transition");
	}

	@Test
	public void InvalidRoomStateTransitionExceptionResultsInConflict() throws NoSuchMethodException, SecurityException {
		ResponseStatus result = AnnotationUtils.findAnnotation(InvalidRoomStateTransitionExceptionHandler.class.getMethod("process", InvalidRoomStateTransitionException.class), ResponseStatus.class);

		assertThat(result).isNotNull();
		assertThat(result.code()).isEqualTo(HttpStatus.CONFLICT);
	}

}
