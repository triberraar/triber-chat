package be.tribersoft.triber.chat.user.domain.impl;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJpaRepository extends JpaRepository<UserEntity, String> {

	Optional<UserEntity> findByUsername(String username);

	Optional<UserEntity> findByUsernameAndActivatedAndValidated(String username, boolean activated, boolean validated);

	Optional<UserEntity> findByEmail(String email);

	Optional<UserEntity> findByIdAndActivated(String id, boolean activated);

	Optional<UserEntity> findByEmailAndActivated(String email, boolean activated);
}
