package be.tribersoft.triber.chat.reset.password.controller;

import javax.inject.Inject;
import javax.validation.Valid;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import be.tribersoft.triber.chat.reset.password.service.api.ResetPasswordService;

@RestController
@RequestMapping("/reset-password")
public class ResetPasswordController {

	@Inject
	private ResetPasswordService resetPasswordService;

	@RequestMapping(method = RequestMethod.POST, consumes = "application/json")
	public void request(@Valid @RequestBody ResetPasswordRequestFromJsonAdapter json) {
		resetPasswordService.request(json);
	}

	@RequestMapping(method = RequestMethod.POST, consumes = "application/json", path = "/{id}")
	public void confirm(@PathVariable("id") String id, @Valid @RequestBody ResetPasswordConfirmationFromJsonAdapter json) {
		resetPasswordService.confirm(id, json);
	}

}
