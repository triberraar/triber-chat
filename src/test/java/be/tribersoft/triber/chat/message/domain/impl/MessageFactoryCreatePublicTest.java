package be.tribersoft.triber.chat.message.domain.impl;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class MessageFactoryCreatePublicTest {

	private static final String OWNER_USERNAME = "owner username";
	private static final String CONTENT = "content";

	private MessageFactory messageFactory = new MessageFactory();

	@Test
	public void createsAPublicMessage() {
		PublicMessageEntity result = messageFactory.createPublic(OWNER_USERNAME, CONTENT);

		assertThat(result.getOwnerUsername()).isEqualTo(OWNER_USERNAME);
		assertThat(result.getContent()).isEqualTo(CONTENT);
	}
}
