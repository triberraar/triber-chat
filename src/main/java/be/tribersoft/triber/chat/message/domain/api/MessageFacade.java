package be.tribersoft.triber.chat.message.domain.api;

public interface MessageFacade {

	Message createPublic(String ownerUsername, String content);

}
