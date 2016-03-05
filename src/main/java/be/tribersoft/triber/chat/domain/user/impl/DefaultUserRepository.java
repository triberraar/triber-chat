package be.tribersoft.triber.chat.domain.user.impl;

import java.util.Optional;

import javax.inject.Inject;
import javax.inject.Named;

import be.tribersoft.triber.chat.domain.user.api.UserRepository;

@Named
public class DefaultUserRepository implements UserRepository {
	@Inject
	private UserJpaRepository userJpaRepository;

	@Override
	public Optional<UserEntity> findByUsername(String username) {
		return userJpaRepository.findByUsername(username);
	}
}
