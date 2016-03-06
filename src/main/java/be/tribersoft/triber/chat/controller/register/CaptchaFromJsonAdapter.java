package be.tribersoft.triber.chat.controller.register;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CaptchaFromJsonAdapter {
	private boolean success;

	public boolean getSuccess() {
		return success;
	}
}
