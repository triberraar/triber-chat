package be.tribersoft.triber.chat.reset.password.domain.impl;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import be.tribersoft.triber.chat.reset.password.domain.api.ResetPassword;
import be.tribersoft.triber.chat.reset.password.domain.api.ResetPasswordConfirmationMessage;
import be.tribersoft.triber.chat.reset.password.domain.api.ResetPasswordFacade;
import be.tribersoft.triber.chat.reset.password.domain.api.ResetPasswordMessage;
import be.tribersoft.triber.chat.user.domain.api.User;
import be.tribersoft.triber.chat.user.domain.api.UserFacade;
import be.tribersoft.triber.chat.user.domain.api.UserRepository;

@Named
public class DefaultResetPasswordFacade implements ResetPasswordFacade {

	@Inject
	private UserRepository userRepository;
	@Inject
	private ResetPasswordFactory resetPasswordFactory;
	@Inject
	private DefaultResetPasswordRepository defaultResetPasswordRepository;
	@Inject
	private UserFacade userFacade;

	@Override
	public ResetPasswordEntity request(ResetPasswordMessage resetPasswordMessage) {
		User user = userRepository.getActivatedByEmail(resetPasswordMessage.getEmail());
		return defaultResetPasswordRepository.save(resetPasswordFactory.create(user.getId()));
	}

	@Override
	public void delete(String id) {
		defaultResetPasswordRepository.delete(id);
	}

	@Override
	public void cleanup() {
		List<ResetPasswordEntity> resetPasswordEntities = defaultResetPasswordRepository.findExpired();
		defaultResetPasswordRepository.delete(resetPasswordEntities);
	}

	@Override
	public void confirm(String resetPasswordId, ResetPasswordConfirmationMessage resetPasswordConfirmationMessage) {
		ResetPassword resetPassword = defaultResetPasswordRepository.getById(resetPasswordId);
		userFacade.changePassword(resetPassword.getUserId(), resetPasswordConfirmationMessage.getPassword());
		defaultResetPasswordRepository.delete(resetPasswordId);
	}
}
