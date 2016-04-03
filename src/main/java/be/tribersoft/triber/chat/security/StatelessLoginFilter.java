package be.tribersoft.triber.chat.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.fasterxml.jackson.databind.ObjectMapper;

public class StatelessLoginFilter extends AbstractAuthenticationProcessingFilter {

	private UserDetailsService userDetailsService;
	private TokenAuthenticationService tokenAuthenticationService;

	public StatelessLoginFilter(String urlMapping, TokenAuthenticationService tokenAuthenticationService, UserDetailsService userDetailsService, AuthenticationManager authenticationManager) {
		super(new AntPathRequestMatcher(urlMapping));
		this.userDetailsService = userDetailsService;
		this.tokenAuthenticationService = tokenAuthenticationService;
		setAuthenticationManager(authenticationManager);
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
		UserFromJson user = new ObjectMapper().readValue(request.getInputStream(), UserFromJson.class);
		final UsernamePasswordAuthenticationToken loginToken = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
		return getAuthenticationManager().authenticate(loginToken);

	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {

		final SecurityUser authenticatedUser = userDetailsService.loadUserByUsername(authentication.getName());
		final SecurityUserAuthentication userAuthentication = new SecurityUserAuthentication(authenticatedUser);
		tokenAuthenticationService.addAuthentication(response, userAuthentication);
		SecurityContextHolder.getContext().setAuthentication(userAuthentication);
	}

}
