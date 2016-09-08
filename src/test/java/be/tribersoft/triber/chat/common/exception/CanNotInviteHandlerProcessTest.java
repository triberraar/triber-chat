package be.tribersoft.triber.chat.common.exception;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import be.tribersoft.triber.chat.room.domain.api.CanNotInviteBecauseNotOwnerException;
import be.tribersoft.triber.chat.room.domain.api.CanNotInviteBecauseParticipantNotOnlineException;

public class CanNotInviteHandlerProcessTest {

	private CanNotInviteExceptionHandler handler = new CanNotInviteExceptionHandler();

	@Test
	public void wrapsCanNotInviteBecauseNotOwnerException() {
		CanNotInviteBecauseNotOwnerException exception = new CanNotInviteBecauseNotOwnerException();

		ErrorToJsonAdapter result = handler.process(exception);

		assertThat(result.getErrorCode()).isEqualTo("can.not.invite.because.not.owner");
	}

	@Test
	public void canNotInviteBecauseNotOwnerExceptionResultsInBadRequest() throws NoSuchMethodException, SecurityException {
		ResponseStatus result = AnnotationUtils.findAnnotation(CanNotInviteExceptionHandler.class.getMethod("process", CanNotInviteBecauseNotOwnerException.class), ResponseStatus.class);

		assertThat(result).isNotNull();
		assertThat(result.code()).isEqualTo(HttpStatus.BAD_REQUEST);
	}

	@Test
	public void wrapsCanNotInviteBecauseParticipantNotOnlineException() {
		CanNotInviteBecauseParticipantNotOnlineException exception = new CanNotInviteBecauseParticipantNotOnlineException();

		ErrorToJsonAdapter result = handler.process(exception);

		assertThat(result.getErrorCode()).isEqualTo("can.not.invite.because.participant.not.online");
	}

	@Test
	public void canNotInviteBecauseParticipantNotOnlineExceptionResultsInBadRequest() throws NoSuchMethodException, SecurityException {
		ResponseStatus result = AnnotationUtils.findAnnotation(CanNotInviteExceptionHandler.class.getMethod("process", CanNotInviteBecauseParticipantNotOnlineException.class), ResponseStatus.class);

		assertThat(result).isNotNull();
		assertThat(result.code()).isEqualTo(HttpStatus.BAD_REQUEST);
	}
}
