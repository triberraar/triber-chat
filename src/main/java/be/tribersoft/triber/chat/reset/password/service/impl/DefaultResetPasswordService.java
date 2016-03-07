package be.tribersoft.triber.chat.reset.password.service.impl;

import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;

import be.tribersoft.triber.chat.reset.password.domain.api.ResetPassword;
import be.tribersoft.triber.chat.reset.password.domain.api.ResetPasswordConfirmationMessage;
import be.tribersoft.triber.chat.reset.password.domain.api.ResetPasswordFacade;
import be.tribersoft.triber.chat.reset.password.domain.api.ResetPasswordMessage;
import be.tribersoft.triber.chat.reset.password.domain.api.ResetPasswordRepository;
import be.tribersoft.triber.chat.reset.password.service.api.ResetPasswordService;
import be.tribersoft.triber.chat.user.domain.api.User;
import be.tribersoft.triber.chat.user.domain.api.UserFacade;
import be.tribersoft.triber.chat.user.domain.api.UserRepository;

@Named
@Transactional
public class DefaultResetPasswordService implements ResetPasswordService {

	@Inject
	private ResetPasswordFacade resetPasswordFacade;
	@Inject
	private ResetPasswordMailService resetPasswordMailService;
	@Inject
	private UserRepository userRepository;
	@Inject
	private ResetPasswordRepository resetPasswordRepository;
	@Inject
	private UserFacade userFacade;

	@Override
	public void request(ResetPasswordMessage resetPasswordMessage) {
		ResetPassword resetPassword = resetPasswordFacade.request(resetPasswordMessage);
		User user = userRepository.getActivatedByEmail(resetPasswordMessage.getEmail());
		resetPasswordMailService.sendMail(resetPassword.getId(), user.getUsername(), resetPasswordMessage.getEmail());
	}

	@Override
	public void confirm(String resetPasswordId, ResetPasswordConfirmationMessage resetPasswordConfirmationMessage) {
		ResetPassword resetPassword = resetPasswordRepository.getById(resetPasswordId);
		userFacade.changePassword(resetPassword.getUserId(), resetPasswordConfirmationMessage.getPassword());
		resetPasswordFacade.delete(resetPasswordId);
	}

}
