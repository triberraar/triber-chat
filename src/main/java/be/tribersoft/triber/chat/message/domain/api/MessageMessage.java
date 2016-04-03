package be.tribersoft.triber.chat.message.domain.api;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

public interface MessageMessage {

	@NotEmpty(message = "message.validation.content.empty")
	@Length(max = 2048, message = "message.validation.content.length")
	String getContent();

}
