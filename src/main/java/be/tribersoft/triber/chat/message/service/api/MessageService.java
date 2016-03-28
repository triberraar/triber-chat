package be.tribersoft.triber.chat.message.service.api;

import be.tribersoft.triber.chat.message.domain.api.Message;

public interface MessageService {

	Message create(String ownerUsername, String content);

}
