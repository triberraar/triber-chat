package be.tribersoft.triber.chat.user.service.impl;

import java.util.Collection;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.annotation.Secured;

import be.tribersoft.triber.chat.common.WebSocketService;
import be.tribersoft.triber.chat.user.domain.api.ConnectedUsersRepository;
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
	@Inject
	private WebSocketService webSocketService;
	@Inject
	private ConnectedUsersRepository connectedUsersRepository;

	@Override
	@Secured({ "ROLE_ADMIN" })
	public boolean existsUnvalidated() {
		return userRepository.existsUnvalidated();
	}

	@Override
	@Secured({ "ROLE_ADMIN" })
	public Page<? extends User> findAll(Pageable pageable, Map<String, String> searchParams) {
		return userRepository.findAll(pageable, searchParams);
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
		webSocketService.send("/topic/notifications/validatedUser", user.getUsername());
	}

	@Override
	public Collection<? extends User> findAllConnected() {
		return connectedUsersRepository.findAll();
	}

}
