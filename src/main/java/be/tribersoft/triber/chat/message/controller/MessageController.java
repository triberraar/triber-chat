package be.tribersoft.triber.chat.message.controller;

import java.security.Principal;

import javax.inject.Inject;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import be.tribersoft.triber.chat.message.domain.api.PublicMessage;
import be.tribersoft.triber.chat.message.domain.impl.PublicMessageEntity;
import be.tribersoft.triber.chat.message.service.api.MessageService;

@Controller
public class MessageController {

	@Inject
	private MessageService messageService;
	@Inject
	private SimpMessagingTemplate messagingTemplate;

	@MessageMapping("/message/general")
	public MessageToJsonAdapter generalChat(MessageFromJsonAdapter inputMessage, Principal principal) {
		PublicMessage message = messageService.createPublic(principal.getName(), inputMessage.getContent());
		return new MessageToJsonAdapter(message);
	}

	@MessageMapping("/message/private")
	public void privateChat(PrivateMessageFromJsonAdapter inputMessage, Principal principal) {
		if (inputMessage.getDestination().equals(principal.getName())) {
			throw new IllegalArgumentException("cant message self");
		}
		this.messagingTemplate.convertAndSendToUser(inputMessage.getDestination(), "/topic/message/private", new MessageToJsonAdapter(new PublicMessageEntity(principal.getName(), inputMessage.getContent())));
		this.messagingTemplate.convertAndSendToUser(principal.getName(), "/topic/message/private", new MessageToJsonAdapter(new PublicMessageEntity(principal.getName(), inputMessage.getContent())));
	}
}
