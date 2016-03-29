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
public class DefaultMessageFacadeCreatePublicTest {

	private static final String CONTENT = "content";
	private static final String OWNER_USER_NAME = "owner user name";
	@InjectMocks
	private DefaultMessageFacade defaultMessageFacade;
	@Mock
	private MessageFactory messageFactory;
	@Mock
	private DefaultMessageRepository defaultMessageRepository;
	@Mock
	private PublicMessageEntity publicMessageEntity;

	@Before
	public void setUp() {
		when(messageFactory.createPublic(OWNER_USER_NAME, CONTENT)).thenReturn(publicMessageEntity);
	}

	@Test
	public void createsAndStoresAPublicMessage() {
		defaultMessageFacade.createPublic(OWNER_USER_NAME, CONTENT);

		verify(defaultMessageRepository).save(publicMessageEntity);
	}

}
