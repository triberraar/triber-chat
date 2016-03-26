package be.tribersoft.triber.chat.message.domain.impl;

import javax.inject.Inject;
import javax.inject.Named;

@Named
public class DefaultMessageRepository {

	@Inject
	private MessageJpaRepository messageJpaRepository;

	public void create(MessageEntity messageEntity) {
		messageJpaRepository.save(messageEntity);
	}
}
