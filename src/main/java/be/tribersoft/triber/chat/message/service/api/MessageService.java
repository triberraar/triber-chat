package be.tribersoft.triber.chat.message.service.api;

import be.tribersoft.triber.chat.message.domain.api.PublicMessage;

public interface MessageService {

	PublicMessage createPublic(String ownerUsername, String content);

}
