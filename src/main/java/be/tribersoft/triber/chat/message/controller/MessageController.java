package be.tribersoft.triber.chat.message.controller;

import java.security.Principal;

import javax.inject.Inject;
import javax.validation.Valid;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

import be.tribersoft.triber.chat.common.WebSocketService;
import be.tribersoft.triber.chat.message.domain.api.PrivateMessage;
import be.tribersoft.triber.chat.message.domain.api.PublicMessage;
import be.tribersoft.triber.chat.message.service.api.MessageService;

@Controller
public class MessageController {

	@Inject
	private MessageService messageService;
	@Inject
	private WebSocketService webSocketService;
	@Inject
	private MessageValidator messageValidator;

	@MessageMapping("/message/general")
	public MessageToJsonAdapter generalChat(@Valid MessageFromJsonAdapter inputMessage, Principal principal) {
		PublicMessage message = messageService.createPublic(principal.getName(), inputMessage.getContent());
		return new MessageToJsonAdapter(message);
	}

	@MessageMapping("/message/private")
	public void privateChat(@Valid PrivateMessageFromJsonAdapter inputMessage, Principal principal) {
		messageValidator.validatePrivate(inputMessage, principal);

		PrivateMessage message = messageService.createPrivate(inputMessage.getContent(), inputMessage.getTo(), inputMessage.getFrom());
		webSocketService.sendToUser(message.getFrom(), "/topic/message/private", new PrivateMessageToJsonAdapter(message));
		webSocketService.sendToUser(message.getTo(), "/topic/message/private", new PrivateMessageToJsonAdapter(message));
	}
}
