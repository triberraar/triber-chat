package be.tribersoft.triber.chat.security;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import be.tribersoft.triber.chat.user.domain.api.User;
import be.tribersoft.triber.chat.user.domain.api.UserRepository;

@RunWith(MockitoJUnitRunner.class)
public class UserDetailsServiceLoadByUsernameTest {

	private static final String USERNAME = "username";

	@InjectMocks
	private UserDetailsService userDetailsService;

	@Mock
	private UserRepository userRepository;
	@Mock
	private User user;

	@Test
	public void returnsUserWhenFound() {
		when(userRepository.findActivatedAndValidatedByUsername(USERNAME)).thenReturn(Optional.of(user));

		SecurityUser result = userDetailsService.loadUserByUsername(USERNAME);

		assertThat(result.getUser()).isEqualTo(user);
	}

	@Test(expected = UsernameNotFoundException.class)
	public void failsWhenNotFound() {
		when(userRepository.findActivatedAndValidatedByUsername(USERNAME)).thenReturn(Optional.empty());

		userDetailsService.loadUserByUsername(USERNAME);
	}
}
