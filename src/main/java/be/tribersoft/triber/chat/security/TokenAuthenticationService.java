package be.tribersoft.triber.chat.security;

import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import be.tribersoft.triber.chat.domain.user.api.UserRepository;

@Named
public class TokenAuthenticationService {

	private static final String HEADER_NAME = "Authorization";

	@Inject
	private TokenHandler tokenHandler;
	@Inject
	private UserRepository userRepository;

	public void addAuthentication(HttpServletResponse response, SecurityUserAuthentication userAuthentication) {
		response.addHeader(HEADER_NAME, "Bearer " + tokenHandler.toToken(userRepository.findByUsername(userAuthentication.getName()).get()));
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
			try {
				final SecurityUser user = tokenHandler.fromToken(token);
				return new SecurityUserAuthentication(user);
			} catch (AuthenticationException ae) {
				return null;
			}
		}
		return null;
	}
}
