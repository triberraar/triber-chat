package be.tribersoft.triber.chat.message.domain.api;

import org.hibernate.validator.constraints.Length;

public interface MessageMessage {

	@Length(max = 2048)
	String getContent();

}
