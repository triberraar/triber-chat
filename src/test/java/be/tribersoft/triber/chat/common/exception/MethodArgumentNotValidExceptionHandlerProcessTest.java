package be.tribersoft.triber.chat.common.exception;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ResponseStatus;

@RunWith(MockitoJUnitRunner.class)
public class MethodArgumentNotValidExceptionHandlerProcessTest {

	private static final String MESSAGE_2 = "message 2";
	private static final String MESSAGE_1 = "message 1";

	private MethodArgumentNotValidExceptionHandler handler = new MethodArgumentNotValidExceptionHandler();

	@Mock
	private MethodArgumentNotValidException exception;
	@Mock
	private BindingResult bindingResult;
	@Mock
	private ObjectError error1, error2;

	@Before
	public void setUp() {
		when(exception.getBindingResult()).thenReturn(bindingResult);
		when(bindingResult.getAllErrors()).thenReturn(Arrays.asList(error1, error2));
		when(error1.getDefaultMessage()).thenReturn(MESSAGE_1);
		when(error2.getDefaultMessage()).thenReturn(MESSAGE_2);
	}

	@Test
	public void wrapsException() {
		ValidationErrorToJsonAdapter result = handler.process(exception);

		assertThat(result.getErrors()).hasSize(2).contains(MESSAGE_1).contains(MESSAGE_2);
	}

	@Test
	public void resultsInBadRequest() throws NoSuchMethodException, SecurityException {
		ResponseStatus result = AnnotationUtils.findAnnotation(MethodArgumentNotValidExceptionHandler.class.getMethod("process", MethodArgumentNotValidException.class), ResponseStatus.class);

		assertThat(result).isNotNull();
		assertThat(result.code()).isEqualTo(HttpStatus.BAD_REQUEST);
	}

}
