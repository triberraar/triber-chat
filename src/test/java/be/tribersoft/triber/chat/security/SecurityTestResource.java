package be.tribersoft.triber.chat.security;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test/security")
public class SecurityTestResource {

	@RequestMapping(method = RequestMethod.GET)
	public void security() {
		return;
	}

}
