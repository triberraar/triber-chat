package be.tribersoft.triber.chat.user.controller;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import be.tribersoft.triber.chat.register.controller.ActivateRegistrationFromJsonAdapter;
import be.tribersoft.triber.chat.user.domain.api.User;
import be.tribersoft.triber.chat.user.service.api.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

	@Inject
	private UserService userService;

	@RequestMapping(method = RequestMethod.GET, produces = "application/json")
	public Page<UserToJsonAdapter> all(Pageable pageable, @RequestParam Map<String, String> requestParams) {
		Page<? extends User> result = userService.findAll(pageable, requestParams);
		List<UserToJsonAdapter> pageContent = result.getContent().stream().map(user -> new UserToJsonAdapter(user)).collect(Collectors.toList());
		return new PageImpl<>(pageContent, pageable, result.getTotalElements());
	}

	@RequestMapping(value = "/unvalidated", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> existsUnvalidated() {
		if (userService.existsUnvalidated()) {
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@RequestMapping(value = "/{userId}/activate", method = RequestMethod.PUT, consumes = "application/json")
	public void activate(@Valid @RequestBody ActivateRegistrationFromJsonAdapter json, @PathVariable("userId") String userId) {
		userService.activate(userId, json.getPassword());
	}

	@RequestMapping(value = "/{userId}/validate", method = RequestMethod.PUT, consumes = "application/json")
	public void validate(@PathVariable("userId") String userId) {
		userService.validate(userId);
	}

	@RequestMapping(value = "/connected", method = RequestMethod.GET, produces = "application/json")
	public List<UserToJsonAdapter> allConnected() {
		return userService.findAllConnected().stream().map(user -> new UserToJsonAdapter(user)).collect(Collectors.toList());
	}
}
