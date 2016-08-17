package be.tribersoft.triber.chat.user.service.impl;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;

import javax.inject.Inject;
import javax.mail.MessagingException;

import org.apache.commons.io.IOUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.subethamail.wiser.Wiser;

import be.tribersoft.triber.chat.Application;
import be.tribersoft.triber.chat.common.MailAssertions;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
@TestPropertySource(value = "classpath:/application-test.properties")
public class ValidatedUserMailServiceSendMailIT {
	private Wiser wiser;

	@Inject
	private ValidatedUserMailService validatedUserMailService;

	@Before
	public void setUp() {
		wiser = new Wiser();
		wiser.setPort(9999);
		wiser.start();
	}

	@After
	public void tearDown() {
		wiser.stop();
	}

	@Test
	public void sendsAMail() throws MessagingException, IOException {

		validatedUserMailService.sendMail("username", "email@localhost");

		assertThat(wiser.getMessages()).hasSize(1);
		// @formatter:off
		MailAssertions.assertReceivedMessage(wiser)
			.from("triber.chat@gmail.com")
			.to("email@localhost")
			.withSubject("Triber chat: account validated")
			.withContent(IOUtils.toString(new ClassPathResource("mail/validatedmail.html").getInputStream()));
		// @formatter:on
	}
}
