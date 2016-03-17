package be.tribersoft.triber.chat.chat.controller;

import java.security.Principal;
import java.util.Date;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {

	@MessageMapping("/chat/general")
	public MessageToJsonAdapter generalChat(MessageFromJsonAdapter inputMessage, Principal principal) {
		return new MessageToJsonAdapter(inputMessage.getMessage(), principal.getName(), new Date());
	}
}
