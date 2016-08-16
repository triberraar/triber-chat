package be.tribersoft.triber.chat.message.domain.impl;

import javax.persistence.Column;
import javax.persistence.Entity;

import be.tribersoft.triber.chat.message.domain.api.PrivateMessage;

@Entity(name = "privateMessage")
public class PrivateMessageEntity extends AbstractMessageEntity implements PrivateMessage {

	@Column(nullable = false, length = 256, name = "to_user")
	protected String to;
	@Column(nullable = false, length = 256, name = "from_user")
	protected String from;

	protected PrivateMessageEntity() {
		super();
	}

	public PrivateMessageEntity(String content, String to, String from) {
		super();
		this.content = content;
		this.to = to;
		this.from = from;
	}

	@Override
	public String getTo() {
		return to;
	}

	@Override
	public String getFrom() {
		return from;
	}
}
