package be.tribersoft.triber.chat.domain.user.api;

import java.util.Optional;

public interface UserRepository {

	Optional<? extends User> findByUsername(String username);

	Optional<? extends User> findActivatedByUsername(String username);

}
