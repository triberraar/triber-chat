package be.tribersoft.triber.chat.common.exception;

public class BusinessException extends RuntimeException {
	private String errorCode;

	public BusinessException(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorCode() {
		return errorCode;
	}
}
