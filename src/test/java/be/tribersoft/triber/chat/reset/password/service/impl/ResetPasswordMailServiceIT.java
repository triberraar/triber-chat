package be.tribersoft.triber.chat.reset.password.service.impl;

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
import org.subethamail.wiser.Wiser;

import be.tribersoft.triber.chat.Application;
import be.tribersoft.triber.chat.common.MailAssertions;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
@TestPropertySource(value = "classpath:/application-test.properties")
public class ResetPasswordMailServiceIT {

	private Wiser wiser;

	@Inject
	private ResetPasswordMailService resetPasswordMailService;

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

		resetPasswordMailService.sendMail("reset password id", "username", "email@localhost");

		assertThat(wiser.getMessages()).hasSize(1);
		// @formatter:off
		MailAssertions.assertReceivedMessage(wiser)
			.from("triber.chat@gmail.com")
			.to("email@localhost")
			.withSubject("Triber chat: reset password")
			.withContent(IOUtils.toString(new ClassPathResource("mail/resetPasswordmail.html").getInputStream()));
		// @formatter:on
	}
}
