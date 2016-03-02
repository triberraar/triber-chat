package be.tribersoft.triber.chat.configuration;

import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;

import be.tribersoft.triber.chat.domain.User;

@Named
public class TokenAuthenticationService {

	private static final String HEADER_NAME = "X-AUTH-TOKEN";

	@Inject
	private TokenHandler tokenHandler;

	public void addAuthentication(HttpServletResponse response, UserAuthentication userAuthentication) {
		response.addHeader(HEADER_NAME, tokenHandler.toToken(userAuthentication.getDetails()));
	}

	public Authentication getAuthentication(HttpServletRequest request) {
		String token = request.getHeader(HEADER_NAME);
		if (token == null) {
			token = request.getParameter("token");
		}
		if (token != null) {
			final User user = tokenHandler.fromToken(token);
			return new UserAuthentication(user);
		}
		return null;
	}
}
