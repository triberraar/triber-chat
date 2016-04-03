package be.tribersoft.triber.chat.reset.password.service.impl;

import static org.mockito.Mockito.verify;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.boot.context.event.ApplicationReadyEvent;

import be.tribersoft.triber.chat.reset.password.service.api.ResetPasswordService;

@RunWith(MockitoJUnitRunner.class)
public class ApplicationStartupListenerOnApplicationEventTest {

	@InjectMocks
	private ApplicationStartupListener applicationStartupListener;

	@Mock
	private ResetPasswordService resetPasswordService;

	@Mock
	private ApplicationReadyEvent event;

	@Test
	public void cleansupExpiredResetPasswordEntitiesOnStartup() {
		applicationStartupListener.onApplicationEvent(event);

		verify(resetPasswordService).cleanup();
	}
}
