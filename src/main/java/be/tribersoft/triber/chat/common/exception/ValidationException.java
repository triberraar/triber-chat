package be.tribersoft.triber.chat.common.exception;

public class ValidationException extends RuntimeException {

	private String errorCode;

	public ValidationException(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorCode() {
		return errorCode;
	}
}
