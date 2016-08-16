package be.tribersoft.triber.chat.message.controller;

import static org.mockito.Mockito.when;

import java.security.Principal;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import be.tribersoft.triber.chat.common.exception.BusinessException;

@RunWith(MockitoJUnitRunner.class)
public class MessageValidatorValidatePrivateTest {

	private static final String FROM_2 = "from 2";
	private static final String TO = "to";
	private static final String FROM = "from";

	private MessageValidator messageValidator = new MessageValidator();
	@Mock
	private PrivateMessageFromJsonAdapter inputMessage;
	@Mock
	private Principal principal;

	@Before
	public void setUp() {
		when(principal.getName()).thenReturn(FROM);
		when(inputMessage.getFrom()).thenReturn(FROM);
		when(inputMessage.getTo()).thenReturn(TO);
	}

	@Test(expected = BusinessException.class)
	public void failsWhenPrincipalIsSameAsTo() {
		when(inputMessage.getTo()).thenReturn(FROM);

		messageValidator.validatePrivate(inputMessage, principal);
	}

	@Test(expected = BusinessException.class)
	public void failsWhenFromIsNotSameAsPrincipal() {
		when(inputMessage.getFrom()).thenReturn(FROM_2);

		messageValidator.validatePrivate(inputMessage, principal);
	}

	@Test
	public void succeedsOtherwise() {
		messageValidator.validatePrivate(inputMessage, principal);
	}
}
