package be.tribersoft.triber.chat.user.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;
import javax.mail.internet.MimeMessage;

import org.apache.velocity.app.VelocityEngine;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.ui.velocity.VelocityEngineUtils;

@Named
public class ValidatedUserMailService {

	@Inject
	private JavaMailSender mailSender;
	@Inject
	private VelocityEngine velocityEngine;

	public void sendMail(String username, String email) {

		MimeMessagePreparator preparator = new MimeMessagePreparator() {
			@Override
			public void prepare(MimeMessage mimeMessage) throws Exception {
				MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
				message.setTo(email);
				message.setFrom("triber.chat@gmail.com");
				message.setSubject("Triber chat: account validated");
				Map<String, Object> model = new HashMap<>();
				model.put("username", username);
				String text = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, "templates/validate-user.vm", "UTF-8", model);
				message.setText(text, true);
			}
		};
		mailSender.send(preparator);
	}
}
