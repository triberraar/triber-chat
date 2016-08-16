package be.tribersoft.triber.chat.message.domain.impl;

import javax.persistence.Column;
import javax.persistence.Entity;

import be.tribersoft.triber.chat.message.domain.api.PublicMessage;

@Entity(name = "publicMessage")
public class PublicMessageEntity extends AbstractMessageEntity implements PublicMessage {

	@Column(nullable = false)
	protected String ownerUsername;

	protected PublicMessageEntity() {
		super();
	}

	public PublicMessageEntity(String ownerUsername, String content) {
		super();
		this.ownerUsername = ownerUsername;
		this.content = content;
	}

	@Override
	public String getOwnerUsername() {
		return ownerUsername;
	}

}
