package be.tribersoft.triber.chat.common;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.util.List;

import javax.mail.MessagingException;

import org.subethamail.wiser.Wiser;
import org.subethamail.wiser.WiserMessage;

public class MailAssertions {

	private final List<WiserMessage> messages;

	public static MailAssertions assertReceivedMessage(Wiser wiser) {
		return new MailAssertions(wiser.getMessages());
	}

	private MailAssertions(List<WiserMessage> messages) {
		this.messages = messages;
	}

	public MailAssertions from(String from) {
		assertThat(messages.get(0).getEnvelopeSender()).isEqualTo(from);
		return this;
	}

	public MailAssertions to(String to) {
		assertThat(messages.get(0).getEnvelopeReceiver()).isEqualTo(to);
		return this;
	}

	public MailAssertions withSubject(String subject) throws MessagingException {
		assertThat(messages.get(0).getMimeMessage().getSubject()).isEqualTo(subject);
		return this;
	}

	public MailAssertions withContent(String content) throws IOException, MessagingException {
		assertThat((String) messages.get(0).getMimeMessage().getContent()).isEqualToIgnoringWhitespace(content);

		return this;
	}

}
