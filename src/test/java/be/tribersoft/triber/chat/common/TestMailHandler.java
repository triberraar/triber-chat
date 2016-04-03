package be.tribersoft.triber.chat.common;

import org.subethamail.wiser.Wiser;

public class TestMailHandler {

	private Wiser wiser;

	public TestMailHandler() {
		wiser = new Wiser();
		wiser.setPort(9999);
		wiser.start();
	}

	public void stop() {
		wiser.stop();
	}

	public Wiser getWiser() {
		return wiser;
	}

	public boolean hasMessage() {
		return !wiser.getMessages().isEmpty();
	}
}
