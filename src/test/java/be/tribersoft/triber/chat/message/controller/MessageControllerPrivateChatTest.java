package be.tribersoft.triber.chat.message.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.security.Principal;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import be.tribersoft.triber.chat.message.domain.api.PrivateMessage;
import be.tribersoft.triber.chat.message.service.api.MessageService;

@RunWith(MockitoJUnitRunner.class)
public class MessageControllerPrivateChatTest {

	private static final Date CREATION_DATE = new Date();
	private static final String TO = "to";
	private static final String FROM = "from";
	private static final String CONTENT = "content";

	@InjectMocks
	private MessageController messageController;

	@Mock
	private MessageService messageService;
	@Mock
	private PrivateMessage message;
	@Mock
	private Principal principal;
	@Mock
	private PrivateMessageFromJsonAdapter inputMessage;
	@Captor
	private ArgumentCaptor<PrivateMessageToJsonAdapter> messageCaptor;
	@Mock
	private SimpMessagingTemplate messagingTemplate;
	@Mock
	private MessageValidator messageValidator;

	@Before
	public void setUp() {
		when(messageService.createPrivate(CONTENT, TO, FROM)).thenReturn(message);
		when(message.getContent()).thenReturn(CONTENT);
		when(message.getTo()).thenReturn(TO);
		when(message.getFrom()).thenReturn(FROM);
		when(message.getCreationDate()).thenReturn(CREATION_DATE);
		when(inputMessage.getContent()).thenReturn(CONTENT);
		when(inputMessage.getTo()).thenReturn(TO);
		when(inputMessage.getFrom()).thenReturn(FROM);
		when(principal.getName()).thenReturn(FROM);
	}

	@Test
	public void createsAndReturnsPublicMessage() {
		messageController.privateChat(inputMessage, principal);

		verify(messageValidator).validatePrivate(inputMessage, principal);
		verify(messagingTemplate).convertAndSendToUser(eq(FROM), eq("/topic/message/private"), messageCaptor.capture());
		assertThat(messageCaptor.getValue().getContent()).isEqualTo(CONTENT);
		assertThat(messageCaptor.getValue().getFrom()).isEqualTo(FROM);
		assertThat(messageCaptor.getValue().getTimestamp()).isEqualTo(CREATION_DATE);
		assertThat(messageCaptor.getValue().getTo()).isEqualTo(TO);
		verify(messagingTemplate).convertAndSendToUser(eq(TO), eq("/topic/message/private"), messageCaptor.capture());
		assertThat(messageCaptor.getValue().getContent()).isEqualTo(CONTENT);
		assertThat(messageCaptor.getValue().getFrom()).isEqualTo(FROM);
		assertThat(messageCaptor.getValue().getTimestamp()).isEqualTo(CREATION_DATE);
		assertThat(messageCaptor.getValue().getTo()).isEqualTo(TO);
	}

}
