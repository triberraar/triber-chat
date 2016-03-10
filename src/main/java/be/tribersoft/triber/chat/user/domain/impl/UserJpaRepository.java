package be.tribersoft.triber.chat.user.domain.impl;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

public interface UserJpaRepository extends JpaRepository<UserEntity, String>, QueryDslPredicateExecutor<UserEntity> {

	Optional<UserEntity> findByUsername(String username);

	Long countByValidated(boolean validated);

	Optional<UserEntity> findByUsernameAndActivatedAndValidated(String username, boolean activated, boolean validated);

	Optional<UserEntity> findByEmail(String email);

	Optional<UserEntity> findByIdAndActivated(String id, boolean activated);

	Optional<UserEntity> findByEmailAndActivated(String email, boolean activated);

	Optional<UserEntity> findByIdAndValidated(String id, boolean validated);

}
