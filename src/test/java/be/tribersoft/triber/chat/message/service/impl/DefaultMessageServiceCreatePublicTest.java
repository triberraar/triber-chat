package be.tribersoft.triber.chat.message.service.impl;

import static org.mockito.Mockito.verify;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import be.tribersoft.triber.chat.message.domain.api.MessageFacade;

@RunWith(MockitoJUnitRunner.class)
public class DefaultMessageServiceCreatePublicTest {

	private static final String OWNER_USERNAME = "owner username";
	private static final String CONTENT = "content";

	@InjectMocks
	private DefaultMessageService defaultMessageService;

	@Mock
	private MessageFacade messageFacade;

	@Test
	public void delegatesToFacade() {
		defaultMessageService.createPublic(OWNER_USERNAME, CONTENT);

		verify(messageFacade).createPublic(OWNER_USERNAME, CONTENT);
	}

}
