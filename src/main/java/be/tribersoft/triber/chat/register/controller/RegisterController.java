package be.tribersoft.triber.chat.register.controller;

import java.io.IOException;

import javax.inject.Inject;
import javax.validation.Valid;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import be.tribersoft.triber.chat.register.service.api.RegisterService;

@RestController
@RequestMapping("/register")
public class RegisterController {

	@Inject
	private CaptchaVerificator captchaVerificator;

	@Inject
	private RegisterService registerService;

	@RequestMapping(method = RequestMethod.POST, consumes = "application/json")
	public void register(@Valid @RequestBody RegisterFromJsonAdapter json) throws JsonParseException, JsonMappingException, UnsupportedOperationException, IOException {
		captchaVerificator.verify(json.getCaptcha());
		registerService.register(json);
	}

	@RequestMapping(value = "/{userId}/activate", method = RequestMethod.POST, consumes = "application/json")
	public void activate(@Valid @RequestBody ActivateRegistrationFromJsonAdapter json, @PathVariable("userId") String userId) {
		registerService.activate(userId, json.getPassword());
	}

	@RequestMapping(value = "/{userId}/validate", method = RequestMethod.POST, consumes = "application/json")
	public void validate(@PathVariable("userId") String userId) {
		registerService.validate(userId);
	}
}
