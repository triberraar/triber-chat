package be.tribersoft.triber.chat.user.domain.api;

import java.util.Map;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserRepository {

	Optional<? extends User> findActivatedAndValidatedByUsername(String username);

	User getActivatedByEmail(String email);

	boolean existsUnvalidated();

	Page<? extends User> findAll(Pageable pageable, Map<String, String> searchParams);

	User getById(String id);

	Long countAll();

	User getByUsername(String username);

}
