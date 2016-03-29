package be.tribersoft.triber.chat.message.domain.api;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

public interface Message extends MessageMessage {

	@NotEmpty(message = "message.validation.owner.username.empty")
	@Length(max = 256, min = 4, message = "message.validation.owner.username.length")
	String getOwnerUsername();

	@NotNull(message = "message.validation.creation.date.null")
	Date getCreationDate();

}
