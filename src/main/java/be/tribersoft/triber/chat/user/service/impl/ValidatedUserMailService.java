package be.tribersoft.triber.chat.user.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;
import javax.mail.internet.MimeMessage;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import freemarker.template.Configuration;

@Named
public class ValidatedUserMailService {

	@Inject
	private JavaMailSender mailSender;
	@Inject
	private Configuration freemarkerConfiguration;

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
				String text = FreeMarkerTemplateUtils.processTemplateIntoString(freemarkerConfiguration.getTemplate("validate-user.vm"), model);
				message.setText(text, true);
			}
		};
		mailSender.send(preparator);
	}
}
