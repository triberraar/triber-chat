package be.tribersoft.triber.chat.user.service.api;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import be.tribersoft.triber.chat.user.domain.api.User;

public interface UserService {

	boolean existsUnvalidated();

	Page<? extends User> findAll(Pageable pageable, Map<String, String> searchParameters);

	void activate(String userId, String password);

	void validate(String userId);

	List<? extends User> findAllConnected();

}
