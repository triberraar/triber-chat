package be.tribersoft.triber.chat.rest.security;

import java.security.Principal;

import javax.inject.Inject;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import be.tribersoft.triber.chat.domain.user.api.UserRepository;
import be.tribersoft.triber.chat.security.TokenHandler;

@RestController
@RequestMapping("/jwt")
public class JWTController {

	@Inject
	private TokenHandler tokenHandler;
	@Inject
	private UserRepository userRepository;

	@RequestMapping(method = RequestMethod.GET, produces = "application/json")
	public JWTJson getNewToken(Principal principal) {
		return new JWTJson(tokenHandler.toToken(userRepository.findByUsername(principal.getName()).get()));
	}
}
