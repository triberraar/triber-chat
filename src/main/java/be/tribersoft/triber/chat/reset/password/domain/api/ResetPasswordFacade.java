package be.tribersoft.triber.chat.reset.password.domain.api;

public interface ResetPasswordFacade {

	ResetPassword request(ResetPasswordMessage resetPasswordMessage);

	void delete(String id);

	void cleanup();

	void confirm(String resetPasswordId, ResetPasswordConfirmationMessage resetPasswordConfirmationMessage);

}
