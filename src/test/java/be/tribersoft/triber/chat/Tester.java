package be.tribersoft.triber.chat;

import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.junit.Test;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

public class Tester {

	@Test
	public void test() throws MessagingException {
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		mailSender.setHost("smtp.gmail.com");
		mailSender.setPort(587);
		// Set gmail email id
		mailSender.setUsername("triber.chat@gmail.com");
		// Set gmail email password
		mailSender.setPassword("triberchat");
		Properties prop = mailSender.getJavaMailProperties();
		prop.put("mail.transport.protocol", "smtp");
		prop.put("mail.smtp.auth", "true");
		prop.put("mail.smtp.starttls.enable", "true");
		prop.put("mail.debug", "true");
		MimeMessage mimeMessage = mailSender.createMimeMessage();
		MimeMessageHelper mailMsg = new MimeMessageHelper(mimeMessage);
		mailMsg.setFrom("arvindraivns02@gmail.com");
		mailMsg.setTo("arvindraivns03@gmail.com");
		mailMsg.setSubject("Test mail");
		mailMsg.setText("Hello World!");
		mailSender.send(mimeMessage);
	}
}
