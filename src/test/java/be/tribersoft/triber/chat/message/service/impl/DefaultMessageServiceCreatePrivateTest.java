package be.tribersoft.triber.chat.message.service.impl;

import static org.mockito.Mockito.verify;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import be.tribersoft.triber.chat.message.domain.api.MessageFacade;

@RunWith(MockitoJUnitRunner.class)
public class DefaultMessageServiceCreatePrivateTest {

	private static final String CONTENT = "content";
	private static final String TO = "to";
	private static final String FROM = "from";

	@InjectMocks
	private DefaultMessageService defaultMessageService;

	@Mock
	private MessageFacade messageFacade;

	@Test
	public void delegatesToFacade() {
		defaultMessageService.createPrivate(CONTENT, TO, FROM);

		verify(messageFacade).createPrivate(CONTENT, TO, FROM);
	}

}
