package be.tribersoft.triber.chat.security;

import java.util.Optional;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.security.core.userdetails.UsernameNotFoundException;

import be.tribersoft.triber.chat.domain.user.api.User;
import be.tribersoft.triber.chat.domain.user.api.UserRepository;

@Named
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

	@Inject
	private UserRepository userRepository;

	@Override
	public SecurityUser loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<? extends User> foundUser = userRepository.findActivatedByUsername(username);
		if (!foundUser.isPresent()) {
			throw new UsernameNotFoundException("'" + username + "' not found");
		}
		return new SecurityUser(foundUser.get());
	}

}
