package be.tribersoft.triber.chat.security;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import javax.servlet.http.HttpServletRequest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.security.core.Authentication;

@RunWith(MockitoJUnitRunner.class)
public class TokenAuthenticationServiceGetAuthenticationTest {
	private static final String TOKEN = "token";
	private static final String JWT = "Bearer " + TOKEN;
	private static final String HEADER_NAME = "Authorization";

	@InjectMocks
	private TokenAuthenticationService tokenAuthenticationService;

	@Mock
	private HttpServletRequest request;
	@Mock
	private TokenHandler tokenHandler;
	@Mock
	private SecurityUser securityUser;

	@Before
	public void setUp() {
		when(request.getHeader(HEADER_NAME)).thenReturn(JWT);
		when(request.getParameter("jwt")).thenReturn(TOKEN);
		when(tokenHandler.fromToken(TOKEN)).thenReturn(securityUser);
	}

	@Test
	public void looksForTokenInHeaderFirst() {
		when(request.getParameter("jwt")).thenReturn(null);

		Authentication result = tokenAuthenticationService.getAuthentication(request);

		assertThat(((SecurityUserAuthentication) result).getUser()).isEqualTo(securityUser);
	}

	@Test
	public void looksForTokenInQueryParameterSecond() {
		when(request.getHeader(HEADER_NAME)).thenReturn(null);

		Authentication result = tokenAuthenticationService.getAuthentication(request);

		assertThat(((SecurityUserAuthentication) result).getUser()).isEqualTo(securityUser);
	}

	@Test
	public void returnsNullOtherwise() {
		when(request.getParameter("jwt")).thenReturn(null);
		when(request.getHeader(HEADER_NAME)).thenReturn(null);

		Authentication result = tokenAuthenticationService.getAuthentication(request);

		assertThat(result).isNull();
	}
}
