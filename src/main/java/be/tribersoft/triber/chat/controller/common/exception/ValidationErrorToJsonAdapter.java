package be.tribersoft.triber.chat.controller.common.exception;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.web.bind.MethodArgumentNotValidException;

public class ValidationErrorToJsonAdapter {

	private Set<String> errors;

	public ValidationErrorToJsonAdapter(MethodArgumentNotValidException ex) {
		this.errors = ex.getBindingResult().getAllErrors().parallelStream().map(error -> error.getDefaultMessage()).collect(Collectors.toSet());
	}

	public ValidationErrorToJsonAdapter(String error) {
		errors = new HashSet<>();
		errors.add(error);
	}

	public Set<String> getErrors() {
		return errors;
	}

}
