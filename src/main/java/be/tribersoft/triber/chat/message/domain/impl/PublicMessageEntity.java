package be.tribersoft.triber.chat.message.domain.impl;

import javax.persistence.Entity;

@Entity(name = "publicMessage")
public class PublicMessageEntity extends AbstractMessageEntity {

	protected PublicMessageEntity() {
		super();
	}

	public PublicMessageEntity(String ownerUsername, String content) {
		super();
		this.ownerUsername = ownerUsername;
		this.content = content;
	}

}
