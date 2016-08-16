package be.tribersoft.triber.chat.message.domain.api;

public interface MessageFacade {

	PublicMessage createPublic(String ownerUsername, String content);

	PrivateMessage createPrivate(String content, String to, String from);

}
