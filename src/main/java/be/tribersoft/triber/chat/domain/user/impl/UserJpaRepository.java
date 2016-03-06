package be.tribersoft.triber.chat.domain.user.impl;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJpaRepository extends JpaRepository<UserEntity, String> {

	Optional<UserEntity> findByUsername(String username);

	Optional<UserEntity> findByEmail(String email);
}
