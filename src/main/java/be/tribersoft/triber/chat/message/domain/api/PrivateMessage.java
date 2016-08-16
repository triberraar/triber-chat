package be.tribersoft.triber.chat.message.domain.api;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

public interface PrivateMessage extends MessageMessage {

	@NotEmpty(message = "message.validation.to.empty")
	@Length(max = 256, min = 4, message = "message.validation.to.length")
	String getTo();

	@NotEmpty(message = "message.validation.from.empty")
	@Length(max = 256, min = 4, message = "message.validation.from.length")
	String getFrom();

	@NotNull(message = "message.validation.creation.date.null")
	Date getCreationDate();
}
