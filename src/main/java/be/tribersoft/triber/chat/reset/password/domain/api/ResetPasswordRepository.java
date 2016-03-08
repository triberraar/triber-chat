package be.tribersoft.triber.chat.reset.password.domain.api;

import java.util.List;

public interface ResetPasswordRepository {
	List<? extends ResetPassword> findExpired();

}
