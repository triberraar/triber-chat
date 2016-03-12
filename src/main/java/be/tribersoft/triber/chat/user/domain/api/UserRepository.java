package be.tribersoft.triber.chat.user.domain.api;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;

public interface UserRepository {

	Optional<? extends User> findByUsername(String username);

	Optional<? extends User> findActivatedAndValidatedByUsername(String username);

	User getActivatedByEmail(String email);

	boolean existsUnvalidated();

	List<? extends User> findAll(Pageable pageable);

	User getById(String id);

	Long countAll();

}
