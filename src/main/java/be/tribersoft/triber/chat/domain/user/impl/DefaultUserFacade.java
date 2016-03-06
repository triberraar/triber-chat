package be.tribersoft.triber.chat.domain.user.impl;

import javax.inject.Inject;
import javax.inject.Named;

import be.tribersoft.triber.chat.domain.user.api.NonUniqueEmailException;
import be.tribersoft.triber.chat.domain.user.api.NonUniqueUsernameException;
import be.tribersoft.triber.chat.domain.user.api.UserFacade;
import be.tribersoft.triber.chat.domain.user.api.UserMessage;

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
	public void activate(String userId, String password) {
		UserEntity userEntity = defaultUserRepository.getNotActivatedById(userId);
		userEntity.activate(password);
	}

}
