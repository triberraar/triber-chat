package be.tribersoft.triber.chat.reset.password.service.impl;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;

import be.tribersoft.triber.chat.reset.password.service.api.ResetPasswordService;

@Named
public class ApplicationStartupListener implements ApplicationListener<ApplicationReadyEvent> {

	@Inject
	private ResetPasswordService resetPasswordService;

	@Override
	public void onApplicationEvent(ApplicationReadyEvent event) {
		resetPasswordService.cleanup();
	}

}
