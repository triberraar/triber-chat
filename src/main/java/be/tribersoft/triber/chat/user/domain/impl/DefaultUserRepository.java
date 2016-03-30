package be.tribersoft.triber.chat.user.domain.impl;

import java.util.Map;
import java.util.Optional;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.QPageRequest;

import be.tribersoft.triber.chat.user.domain.api.User;
import be.tribersoft.triber.chat.user.domain.api.UserNotFoundException;
import be.tribersoft.triber.chat.user.domain.api.UserRepository;

@Named
public class DefaultUserRepository implements UserRepository {
	@Inject
	private UserJpaRepository userJpaRepository;
	@Inject
	private PredicateFactory predicateFactory;

	private Optional<UserEntity> findByUsername(String username) {
		return userJpaRepository.findByUsername(username);
	}

	public boolean existsByUsername(String username) {
		return userJpaRepository.findByUsername(username).isPresent();
	}

	public boolean existsByEmail(String email) {
		return userJpaRepository.findByEmail(email).isPresent();
	}

	public UserEntity save(UserEntity userEntity) {
		return userJpaRepository.save(userEntity);
	}

	public UserEntity getNotActivatedById(String id) {
		Optional<UserEntity> user = userJpaRepository.findByIdAndActivated(id, false);
		if (!user.isPresent()) {
			throw new UserNotFoundException();
		}
		return user.get();
	}

	@Override
	public UserEntity getActivatedByEmail(String email) {
		Optional<UserEntity> user = userJpaRepository.findByEmailAndActivated(email, true);
		if (!user.isPresent()) {
			throw new UserNotFoundException();
		}
		return user.get();

	}

	@Override
	public Optional<? extends User> findActivatedAndValidatedByUsername(String username) {
		return userJpaRepository.findByUsernameAndActivatedAndValidated(username, true, true);
	}

	public UserEntity getActivatedById(String id) {
		Optional<UserEntity> result = userJpaRepository.findByIdAndActivated(id, true);
		if (!result.isPresent()) {
			throw new UserNotFoundException();
		}
		return result.get();
	}

	public UserEntity getNotValidatedById(String id) {
		Optional<UserEntity> result = userJpaRepository.findByIdAndValidated(id, false);
		if (!result.isPresent()) {
			throw new UserNotFoundException();
		}
		return result.get();
	}

	@Override
	public boolean existsUnvalidated() {
		return userJpaRepository.countByValidated(false) > 0;
	}

	@Override
	public Page<UserEntity> findAll(Pageable pageable, Map<String, String> searchParams) {
		QPageRequest pageRequest = new QPageRequest(pageable.getPageNumber(), pageable.getPageSize(), QUserEntity.userEntity.username.asc());
		return userJpaRepository.findAll(predicateFactory.create(searchParams), pageRequest);
	}

	@Override
	public UserEntity getById(String id) {
		Optional<UserEntity> user = userJpaRepository.findById(id);
		if (!user.isPresent()) {
			throw new UserNotFoundException();
		}
		return user.get();
	}

	@Override
	public UserEntity getByUsername(String username) {
		Optional<UserEntity> user = userJpaRepository.findByUsername(username);
		if (!user.isPresent()) {
			throw new UserNotFoundException();
		}
		return user.get();
	}

	@Override
	public Long countAll() {
		return userJpaRepository.count();
	}
}
