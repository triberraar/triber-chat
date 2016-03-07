package be.tribersoft.triber.chat.user.domain.impl;

import javax.inject.Inject;
import javax.inject.Named;

import be.tribersoft.triber.chat.user.domain.api.NonUniqueEmailException;
import be.tribersoft.triber.chat.user.domain.api.NonUniqueUsernameException;
import be.tribersoft.triber.chat.user.domain.api.UserFacade;
import be.tribersoft.triber.chat.user.domain.api.UserMessage;

@Named
public class DefaultUserFacade implements UserFacade {

	@Inject
	private DefaultUserRepository defaultUserRepository;

	@Inject
	private UserFactory userFactory;

	@Override
	public UserEntity register(UserMessage userMessage) {
		if (defaultUserRepository.existsByUsername(userMessage.getUsername())) {
			throw new NonUniqueUsernameException();
		}
		if (defaultUserRepository.existsByEmail(userMessage.getEmail())) {
			throw new NonUniqueEmailException();
		}
		return defaultUserRepository.save(userFactory.create(userMessage));
	}

	@Override
	public void activate(String id, String password) {
		UserEntity userEntity = defaultUserRepository.getNotActivatedById(id);
		userEntity.activate(password);
	}

	@Override
	public void changePassword(String id, String password) {
		defaultUserRepository.getActivatedById(id).changePassword(password);
	}

}
