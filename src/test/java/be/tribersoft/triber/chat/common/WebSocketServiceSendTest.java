package be.tribersoft.triber.chat.common;

import static org.mockito.Mockito.verify;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.messaging.simp.SimpMessagingTemplate;

@RunWith(MockitoJUnitRunner.class)
public class WebSocketServiceSendTest {

	private static final String CHANNEL = "channel";
	private static final String MESSAGE = "message";

	@InjectMocks
	private WebSocketService webSocketService;

	@Mock
	private SimpMessagingTemplate template;

	@Test
	public void sendsTheMessageToTheChannel() {
		webSocketService.send(CHANNEL, MESSAGE);

		verify(template).convertAndSend(CHANNEL, MESSAGE);
	}
}
