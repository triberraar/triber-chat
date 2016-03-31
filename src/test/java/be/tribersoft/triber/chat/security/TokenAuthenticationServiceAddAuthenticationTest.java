package be.tribersoft.triber.chat.security;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.servlet.http.HttpServletResponse;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import be.tribersoft.triber.chat.user.domain.api.User;
import be.tribersoft.triber.chat.user.domain.api.UserRepository;

@RunWith(MockitoJUnitRunner.class)
public class TokenAuthenticationServiceAddAuthenticationTest {

	private static final String TOKEN = "token";
	private static final String NAME = "name";
	private static final String HEADER_NAME = "Authorization";

	@InjectMocks
	private TokenAuthenticationService tokenAuthenticationService;

	@Mock
	private TokenHandler tokenHandler;
	@Mock
	private UserRepository userRepository;
	@Mock
	private SecurityUserAuthentication userAuthentication;
	@Mock
	private HttpServletResponse response;
	@Mock
	private User user;

	@Before
	public void setUp() {
		when(userAuthentication.getName()).thenReturn(NAME);
		when(userRepository.getByUsername(NAME)).thenReturn(user);
		when(tokenHandler.toToken(user)).thenReturn(TOKEN);
	}

	@Test
	public void addsJwtTokenToHeader() {
		tokenAuthenticationService.addAuthentication(response, userAuthentication);

		verify(response).addHeader(HEADER_NAME, "Bearer " + TOKEN);
	}
}
