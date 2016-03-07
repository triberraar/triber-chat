package be.tribersoft.triber.chat.reset.password.domain.impl;

import java.util.Optional;

import javax.inject.Inject;
import javax.inject.Named;

import be.tribersoft.triber.chat.reset.password.domain.api.ResetPasswordNotFoundException;
import be.tribersoft.triber.chat.reset.password.domain.api.ResetPasswordRepository;

@Named
public class DefaultResetPasswordRepository implements ResetPasswordRepository {

	@Inject
	private ResetPasswordJpaRepository resetPasswordJpaRepository;

	public ResetPasswordEntity save(ResetPasswordEntity resetPasswordEntity) {
		return resetPasswordJpaRepository.save(resetPasswordEntity);
	}

	@Override
	public ResetPasswordEntity getById(String id) {
		Optional<ResetPasswordEntity> result = resetPasswordJpaRepository.findById(id);
		if (!result.isPresent()) {
			throw new ResetPasswordNotFoundException();
		}
		return result.get();
	}

	public void delete(String id) {
		resetPasswordJpaRepository.delete(id);
	}

}
