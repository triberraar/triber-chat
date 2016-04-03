package be.tribersoft.triber.chat.common.exception;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import be.tribersoft.triber.chat.register.controller.ActivateRegistrationFromJsonAdapter;
import be.tribersoft.triber.chat.user.domain.api.CanNotActivateUserException;

@RestController
@RequestMapping("/test/exception")
public class ExceptionTestResource {

	@RequestMapping(method = RequestMethod.GET, produces = "application/json", path = "/can-not-activate-user")
	public void throwsCanNotActivateUserException() {
		throw new CanNotActivateUserException();
	}

	@RequestMapping(method = RequestMethod.GET, produces = "application/json", path = "/not-found")
	public void throwsNotFoundException() {
		throw new NotFoundException("error code");
	}

	@RequestMapping(method = RequestMethod.GET, produces = "application/json", path = "/validation")
	public void throwsValidationException() {
		throw new ValidationException("error code");
	}

	@RequestMapping(method = RequestMethod.POST, produces = "application/json", consumes = "application/json", path = "/method-argument-not-valid")
	public void throwsMethodArgumentNotValidException(@Valid @RequestBody ActivateRegistrationFromJsonAdapter body) {
		return;
	}

}
