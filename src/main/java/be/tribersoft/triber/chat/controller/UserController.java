package be.tribersoft.triber.chat.controller;

import java.security.Principal;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

	@RequestMapping(path = "/user", method = RequestMethod.GET)
	public Principal user(Principal user) {
		return user;
	}
}
