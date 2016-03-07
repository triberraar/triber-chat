package be.tribersoft.triber.chat.reset.password.service.impl;

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
public class ResetPasswordMailService {

	@Inject
	private JavaMailSender mailSender;
	@Inject
	private VelocityEngine velocityEngine;
	@Value("${mail.sender.server.address}")
	private String serverAddress;

	public void sendMail(String resetPasswordId, String username, String email) {

		MimeMessagePreparator preparator = new MimeMessagePreparator() {
			@Override
			public void prepare(MimeMessage mimeMessage) throws Exception {
				MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
				message.setTo(email);
				message.setFrom("triber.chat@gmail.com");
				message.setSubject("Triber chat: reset password");
				Map<String, Object> model = new HashMap<>();
				model.put("email", email);
				model.put("username", username);
				model.put("resetPasswordLink", serverAddress + "/#/reset-password/" + resetPasswordId);
				String text = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, "templates/reset-password.vm", "UTF-8", model);
				message.setText(text, true);
			}
		};
		mailSender.send(preparator);
	}
}
