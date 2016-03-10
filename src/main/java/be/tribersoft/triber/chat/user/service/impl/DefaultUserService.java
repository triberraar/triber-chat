package be.tribersoft.triber.chat.user.service.impl;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.security.access.annotation.Secured;

import be.tribersoft.triber.chat.user.domain.api.UserRepository;
import be.tribersoft.triber.chat.user.service.api.UserService;

@Named
public class DefaultUserService implements UserService {
	@Inject
	private UserRepository userRepository;

	@Override
	@Secured({ "ROLE_ADMIN" })
	public boolean existsUnvalidated() {
		return userRepository.existsUnvalidated();
	}

}
