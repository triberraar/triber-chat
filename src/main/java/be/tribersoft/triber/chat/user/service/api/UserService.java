package be.tribersoft.triber.chat.user.service.api;

import java.util.List;

import org.springframework.data.domain.Pageable;

import be.tribersoft.triber.chat.user.domain.api.User;

public interface UserService {

	boolean existsUnvalidated();

	List<? extends User> findAll(Pageable pageable);

	void activate(String userId, String password);

	void validate(String userId);

}
