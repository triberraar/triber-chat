package be.tribersoft.triber.chat.user.domain.api;

import java.util.Optional;

public interface UserRepository {

	Optional<? extends User> findByUsername(String username);

	Optional<? extends User> findActivatedAndValidatedByUsername(String username);

	User getActivatedByEmail(String email);

}
