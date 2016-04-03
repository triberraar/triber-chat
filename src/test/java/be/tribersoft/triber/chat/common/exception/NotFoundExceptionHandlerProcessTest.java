package be.tribersoft.triber.chat.common.exception;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class NotFoundExceptionHandlerProcessTest {

	private static final String ERROR_CODE = "error code";
	private NotFoundExceptionHandler handler = new NotFoundExceptionHandler();

	@Test
	public void wrapsException() {
		NotFoundException exception = new NotFoundException(ERROR_CODE);

		ErrorToJsonAdapter result = handler.process(exception);

		assertThat(result.getErrorCode()).isEqualTo(ERROR_CODE);
	}

	@Test
	public void resultsInNotFound() throws NoSuchMethodException, SecurityException {
		ResponseStatus result = AnnotationUtils.findAnnotation(NotFoundExceptionHandler.class.getMethod("process", NotFoundException.class), ResponseStatus.class);

		assertThat(result).isNotNull();
		assertThat(result.code()).isEqualTo(HttpStatus.NOT_FOUND);
	}

}
