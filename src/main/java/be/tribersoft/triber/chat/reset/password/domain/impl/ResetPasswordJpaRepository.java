package be.tribersoft.triber.chat.reset.password.domain.impl;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ResetPasswordJpaRepository extends JpaRepository<ResetPasswordEntity, String> {

	public Optional<ResetPasswordEntity> findById(String id);
}
