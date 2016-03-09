package be.tribersoft.triber.chat.register.controller;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import be.tribersoft.triber.chat.register.service.api.RegisterService;

@RunWith(MockitoJUnitRunner.class)
public class RegisterControllerRegisterTest {

	private static final String CAPTCHA = "captcha";

	@InjectMocks
	private RegisterController registerController;

	@Mock
	private CaptchaVerificator captchaVerificator;
	@Mock
	private RegisterService registerService;
	@Mock
	private RegisterFromJsonAdapter json;

	@Before
	public void setUp() {
		when(json.getCaptcha()).thenReturn(CAPTCHA);
	}

	@Test
	public void validatesCaptchaAndRegisters() throws JsonParseException, JsonMappingException, UnsupportedOperationException, IOException {
		registerController.register(json);

		verify(captchaVerificator).verify(CAPTCHA);
		verify(registerService).register(json);
	}

}
