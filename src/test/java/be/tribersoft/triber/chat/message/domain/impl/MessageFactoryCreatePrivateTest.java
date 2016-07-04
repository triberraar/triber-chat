package be.tribersoft.triber.chat.message.domain.impl;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class MessageFactoryCreatePrivateTest {

	private static final String CONTENT = "content";
	private static final String TO = "to";
	private static final String FROM = "from";

	private MessageFactory messageFactory = new MessageFactory();

	@Test
	public void createsAPrivateMessage() {
		PrivateMessageEntity result = messageFactory.createPrivate(CONTENT, TO, FROM);

		assertThat(result.getContent()).isEqualTo(CONTENT);
		assertThat(result.getTo()).isEqualTo(TO);
		assertThat(result.getFrom()).isEqualTo(FROM);
	}
}
