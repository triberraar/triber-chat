package be.tribersoft.triber.chat.register.controller;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CaptchaFromJsonAdapter {
	private boolean success;

	public boolean getSuccess() {
		return success;
	}
}
