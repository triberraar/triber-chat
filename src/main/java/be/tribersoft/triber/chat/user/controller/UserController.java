package be.tribersoft.triber.chat.user.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mysema.query.types.Predicate;

import be.tribersoft.triber.chat.user.domain.impl.QUserEntity;
import be.tribersoft.triber.chat.user.domain.impl.UserEntity;
import be.tribersoft.triber.chat.user.domain.impl.UserJpaRepository;
import be.tribersoft.triber.chat.user.service.api.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

	@Inject
	private UserJpaRepository userJpaRepository;
	@Inject
	private UserService userService;

	@RequestMapping(method = RequestMethod.GET, produces = "application/json")
	public List<UserEntity> register(@RequestParam(name = "username", required = false) Optional<String> username) {
		QUserEntity userentity = QUserEntity.userEntity;
		Predicate pred = userentity.username.contains(username.isPresent() ? username.get() : "");
		ArrayList<UserEntity> result = new ArrayList<UserEntity>();
		userJpaRepository.findAll(pred).forEach(result::add);
		return result;
	}

	@RequestMapping(value = "/unvalidated", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> existsUnvalidated() {
		if (userService.existsUnvalidated()) {
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

}
