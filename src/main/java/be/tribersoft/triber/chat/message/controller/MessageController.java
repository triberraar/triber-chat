package be.tribersoft.triber.chat.message.controller;

import java.security.Principal;

import javax.inject.Inject;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

import be.tribersoft.triber.chat.message.domain.api.Message;
import be.tribersoft.triber.chat.message.service.api.MessageService;

@Controller
public class MessageController {

	@Inject
	private MessageService messageService;

	@MessageMapping("/message/general")
	public MessageToJsonAdapter generalChat(MessageFromJsonAdapter inputMessage, Principal principal) {
		Message message = messageService.createPublic(principal.getName(), inputMessage.getContent());
		return new MessageToJsonAdapter(message);
	}
}
