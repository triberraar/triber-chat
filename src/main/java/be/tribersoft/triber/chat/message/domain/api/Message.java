package be.tribersoft.triber.chat.message.domain.api;

import java.util.Date;

public interface Message extends MessageMessage {

	String getOwnerUsername();

	Date getCreationDate();

}
