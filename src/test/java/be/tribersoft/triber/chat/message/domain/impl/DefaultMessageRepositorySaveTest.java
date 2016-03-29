package be.tribersoft.triber.chat.message.domain.impl;

import static org.mockito.Mockito.verify;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class DefaultMessageRepositorySaveTest {

	@InjectMocks
	private DefaultMessageRepository defaultMessageRepository;

	@Mock
	private MessageJpaRepository messageJpaRepository;

	@Mock
	private AbstractMessageEntity messageEntity;

	@Test
	public void delegatesToJpaRepository() {
		defaultMessageRepository.save(messageEntity);

		verify(messageJpaRepository).save(messageEntity);
	}
}
