package be.tribersoft.triber.chat.reset.password.domain.api;

public interface ResetPasswordFacade {

	ResetPassword request(ResetPasswordMessage resetPasswordMessage);

	void cleanup();

	void confirm(String resetPasswordId, ResetPasswordConfirmationMessage resetPasswordConfirmationMessage);

}
