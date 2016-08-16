package be.tribersoft.triber.chat.message.controller;

import java.security.Principal;

import javax.inject.Named;

import be.tribersoft.triber.chat.common.exception.BusinessException;

@Named
public class MessageValidator {

	public void validatePrivate(PrivateMessageFromJsonAdapter inputMessage, Principal principal) {

		if (inputMessage.getTo().equals(principal.getName())) {
			throw new BusinessException("message.private.to.and.from.same");
		}
		if (!inputMessage.getFrom().equals(principal.getName())) {
			throw new BusinessException("message.private.from.and.sender.different");
		}
	}

}
