package be.tribersoft.triber.chat.security;

import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;

import be.tribersoft.triber.chat.user.domain.api.UserRepository;

@Named
public class TokenAuthenticationService {

	private static final String HEADER_NAME = "Authorization";

	@Inject
	private TokenHandler tokenHandler;
	@Inject
	private UserRepository userRepository;

	public void addAuthentication(HttpServletResponse response, SecurityUserAuthentication userAuthentication) {
		response.addHeader(HEADER_NAME, "Bearer " + tokenHandler.toToken(userRepository.getByUsername(userAuthentication.getName())));
	}

	public Authentication getAuthentication(HttpServletRequest request) {
		String token = request.getHeader(HEADER_NAME);
		if (token != null) {
			token = token.substring(7, token.length());
		}
		if (token == null) {
			token = request.getParameter("jwt");
		}
		if (token != null) {
			final SecurityUser user = tokenHandler.fromToken(token);
			return new SecurityUserAuthentication(user);
		}
		return null;
	}
}
