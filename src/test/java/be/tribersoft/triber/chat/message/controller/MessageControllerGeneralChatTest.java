package be.tribersoft.triber.chat.message.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.security.Principal;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import be.tribersoft.triber.chat.message.domain.api.PublicMessage;
import be.tribersoft.triber.chat.message.service.api.MessageService;

@RunWith(MockitoJUnitRunner.class)
public class MessageControllerGeneralChatTest {

	private static final Date CREATION_DATE = new Date();
	private static final String OWNER_USERNAME = "owner username";
	private static final String CONTENT = "content";

	@InjectMocks
	private MessageController messageController;

	@Mock
	private MessageService messageService;
	@Mock
	private PublicMessage message;
	@Mock
	private MessageFromJsonAdapter inputMessage;
	@Mock
	private Principal principal;

	@Before
	public void setUp() {
		when(messageService.createPublic(OWNER_USERNAME, CONTENT)).thenReturn(message);
		when(message.getContent()).thenReturn(CONTENT);
		when(message.getOwnerUsername()).thenReturn(OWNER_USERNAME);
		when(message.getCreationDate()).thenReturn(CREATION_DATE);
		when(inputMessage.getContent()).thenReturn(CONTENT);
		when(principal.getName()).thenReturn(OWNER_USERNAME);
	}

	@Test
	public void createsAndReturnsPublicMessage() {
		MessageToJsonAdapter result = messageController.generalChat(inputMessage, principal);

		assertThat(result.getContent()).isEqualTo(CONTENT);
		assertThat(result.getTimestamp()).isEqualTo(CREATION_DATE);
		assertThat(result.getUsername()).isEqualTo(OWNER_USERNAME);
	}

}
