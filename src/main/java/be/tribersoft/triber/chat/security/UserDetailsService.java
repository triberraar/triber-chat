package be.tribersoft.triber.chat.security;

import java.util.Optional;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.security.core.userdetails.UsernameNotFoundException;

import be.tribersoft.triber.chat.user.domain.api.User;
import be.tribersoft.triber.chat.user.domain.api.UserRepository;

@Named
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

	@Inject
	private UserRepository userRepository;

	@Override
	public SecurityUser loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> foundUser = userRepository.findActivatedAndValidatedByUsername(username);
		if (!foundUser.isPresent()) {
			throw new UsernameNotFoundException("'" + username + "' not found");
		}
		return new SecurityUser(foundUser.get());
	}

}
