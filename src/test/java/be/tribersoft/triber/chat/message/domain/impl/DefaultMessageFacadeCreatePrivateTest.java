package be.tribersoft.triber.chat.message.domain.impl;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class DefaultMessageFacadeCreatePrivateTest {

	private static final String CONTENT = "content";
	private static final String TO = "to";
	private static final String FROM = "from";
	@InjectMocks
	private DefaultMessageFacade defaultMessageFacade;
	@Mock
	private MessageFactory messageFactory;
	@Mock
	private DefaultMessageRepository defaultMessageRepository;
	@Mock
	private PrivateMessageEntity privateMessageEntity;

	@Before
	public void setUp() {
		when(messageFactory.createPrivate(CONTENT, TO, FROM)).thenReturn(privateMessageEntity);
	}

	@Test
	public void createsAndStoresAPrivateMessage() {
		defaultMessageFacade.createPrivate(CONTENT, TO, FROM);

		verify(defaultMessageRepository).save(privateMessageEntity);
	}

}
