package be.tribersoft.triber.chat.user.service.impl;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;

import org.springframework.data.domain.Pageable;
import org.springframework.security.access.annotation.Secured;

import be.tribersoft.triber.chat.user.domain.api.User;
import be.tribersoft.triber.chat.user.domain.api.UserFacade;
import be.tribersoft.triber.chat.user.domain.api.UserRepository;
import be.tribersoft.triber.chat.user.service.api.UserService;

@Named
@Transactional
public class DefaultUserService implements UserService {
	@Inject
	private UserRepository userRepository;
	@Inject
	private UserFacade userFacade;
	@Inject
	private ValidatedUserMailService validatedUserMailService;

	@Override
	@Secured({ "ROLE_ADMIN" })
	public boolean existsUnvalidated() {
		return userRepository.existsUnvalidated();
	}

	@Override
	@Secured({ "ROLE_ADMIN" })
	public List<? extends User> findAll(Pageable pageable) {
		return userRepository.findAll(pageable);
	}

	@Override
	public void activate(String userId, String password) {
		userFacade.activate(userId, password);
	}

	@Override
	@Secured({ "ROLE_ADMIN" })
	public void validate(String userId) {
		userFacade.validate(userId);
		User user = userRepository.getById(userId);
		validatedUserMailService.sendMail(user.getUsername(), user.getEmail());
	}
}
