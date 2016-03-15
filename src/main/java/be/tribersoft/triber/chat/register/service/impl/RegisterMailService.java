package be.tribersoft.triber.chat.register.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;
import javax.mail.internet.MimeMessage;

import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.ui.velocity.VelocityEngineUtils;

@Named
public class RegisterMailService {

	@Inject
	private JavaMailSender mailSender;
	@Inject
	private VelocityEngine velocityEngine;
	@Value("${mail.sender.server.address}")
	private String serverAddress;

	public void sendMail(String username, String userId, String email) {

		MimeMessagePreparator preparator = new MimeMessagePreparator() {
			@Override
			public void prepare(MimeMessage mimeMessage) throws Exception {
				MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
				message.setTo(email);
				message.setFrom("triber.chat@gmail.com");
				message.setSubject("Triber chat: activate account");
				Map<String, Object> model = new HashMap<>();
				model.put("username", username);
				model.put("activationLink", serverAddress + "/#/activate-registration/" + userId);
				String text = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, "templates/activate-registration.vm", "UTF-8", model);
				message.setText(text, true);
			}
		};
		mailSender.send(preparator);
	}
}
