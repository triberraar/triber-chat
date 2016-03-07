package be.tribersoft.triber.chat.common.exception;

public class ErrorToJsonAdapter {
	private String errorCode;

	public ErrorToJsonAdapter(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorCode() {
		return errorCode;
	}
}
