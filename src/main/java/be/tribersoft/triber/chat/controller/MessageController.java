package be.tribersoft.triber.chat.controller;

import java.security.Principal;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@Controller
public class MessageController {

	/*
	 * app destinations: send /app/appQuestions: app from config appQuestions
	 * from messagemapping server automagically sends it to /topic/appQuestions
	 * /topic from magic /appQuestions because it came in into appQuestions
	 */

	@MessageMapping("/appQuestions")
	public String process(String message, Principal principal) throws Exception {
		return "server echos " + message + " by " + principal.getName();
	}

}
