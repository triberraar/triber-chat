package be.tribersoft.triber.chat.security;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.internal.util.reflection.Whitebox;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.security.authentication.InsufficientAuthenticationException;

import com.google.common.collect.Sets;

import be.tribersoft.triber.chat.user.domain.api.Role;
import be.tribersoft.triber.chat.user.domain.api.User;

@RunWith(MockitoJUnitRunner.class)
public class TokenHandlerFromToTokenTest {

	private static final String USERNAME = "username";

	@InjectMocks
	private TokenHandler tokenHandler;

	@Mock
	private UserDetailsService userDetailsService;
	@Mock
	private SecurityUser securityUser;
	@Mock
	private User user;

	@Before
	public void setUp() {
		Whitebox.setInternalState(tokenHandler, "expiration", 1);
		Whitebox.setInternalState(tokenHandler, "secret", "secret");
		when(userDetailsService.loadUserByUsername(USERNAME)).thenReturn(securityUser);
		when(securityUser.getUsername()).thenReturn(USERNAME);
		when(user.getUsername()).thenReturn(USERNAME);
		when(user.getRoles()).thenReturn(Sets.newHashSet(Role.ROLE_USER));
	}

	@Test
	public void canDecodeEncodedToken() {
		String token = tokenHandler.toToken(user);
		SecurityUser tokenSecurityUser = tokenHandler.fromToken(token);

		assertThat(tokenSecurityUser).isSameAs(securityUser);
	}

	@Test(expected = InsufficientAuthenticationException.class)
	public void failsWhenTokenCanNotBeDecoded() {
		tokenHandler.fromToken("invalid token");
	}

}
