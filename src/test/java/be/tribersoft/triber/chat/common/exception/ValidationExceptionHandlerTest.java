package be.tribersoft.triber.chat.common.exception;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class ValidationExceptionHandlerTest {

	private static final String ERROR_CODE = "error code";
	private ValidationExceptionHandler handler = new ValidationExceptionHandler();

	@Test
	public void wrapsException() {
		ValidationException exception = new ValidationException(ERROR_CODE);

		ValidationErrorToJsonAdapter result = handler.process(exception);

		assertThat(result.getErrors()).hasSize(1).contains(ERROR_CODE);
	}

	@Test
	public void resultsInBadRequest() throws NoSuchMethodException, SecurityException {
		ResponseStatus result = AnnotationUtils.findAnnotation(ValidationExceptionHandler.class.getMethod("process", ValidationException.class), ResponseStatus.class);

		assertThat(result).isNotNull();
		assertThat(result.code()).isEqualTo(HttpStatus.BAD_REQUEST);
	}
}
