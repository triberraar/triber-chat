package be.tribersoft.triber.chat.message.controller;

import java.security.Principal;

import javax.inject.Inject;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import be.tribersoft.triber.chat.message.domain.api.PrivateMessage;
import be.tribersoft.triber.chat.message.domain.api.PublicMessage;
import be.tribersoft.triber.chat.message.service.api.MessageService;

@Controller
public class MessageController {

	@Inject
	private MessageService messageService;
	@Inject
	private SimpMessagingTemplate messagingTemplate;
	@Inject
	private MessageValidator messageValidator;

	@MessageMapping("/message/general")
	public MessageToJsonAdapter generalChat(MessageFromJsonAdapter inputMessage, Principal principal) {
		PublicMessage message = messageService.createPublic(principal.getName(), inputMessage.getContent());
		return new MessageToJsonAdapter(message);
	}

	@MessageMapping("/message/private")
	public void privateChat(PrivateMessageFromJsonAdapter inputMessage, Principal principal) {
		messageValidator.validatePrivate(inputMessage, principal);

		PrivateMessage message = messageService.createPrivate(inputMessage.getContent(), inputMessage.getTo(), inputMessage.getFrom());
		messagingTemplate.convertAndSendToUser(message.getFrom(), "/topic/message/private", new PrivateMessageToJsonAdapter(message));
		messagingTemplate.convertAndSendToUser(message.getTo(), "/topic/message/private", new PrivateMessageToJsonAdapter(message));
	}
}
