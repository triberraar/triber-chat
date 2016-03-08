package be.tribersoft.triber.chat.reset.password.service.api;

import be.tribersoft.triber.chat.reset.password.domain.api.ResetPasswordConfirmationMessage;
import be.tribersoft.triber.chat.reset.password.domain.api.ResetPasswordMessage;

public interface ResetPasswordService {

	void request(ResetPasswordMessage resetPasswordMessage);

	void confirm(String resetPasswordId, ResetPasswordConfirmationMessage resetPasswordConfirmationMessage);

	void cleanup();

}
