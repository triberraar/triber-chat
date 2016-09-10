package be.tribersoft.triber.chat.common;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.messaging.simp.SimpMessagingTemplate;

@Named
public class WebSocketService {

	@Inject
	private SimpMessagingTemplate template;

	public void send(String channel, Object message) {
		template.convertAndSend(channel, message);
	}

	public void sendToUser(String user, String channel, Object message) {
		template.convertAndSendToUser(user, channel, message);
	}
}
