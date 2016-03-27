package be.tribersoft.triber.chat.message.domain.impl;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.NotEmpty;

import be.tribersoft.triber.chat.common.DateFactory;
import be.tribersoft.triber.chat.common.jpa.CryptoConverter;
import be.tribersoft.triber.chat.message.domain.api.Message;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class AbstractMessageEntity implements Message {
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid2")
	@Column(name = "id", unique = true, nullable = false)
	private String id;

	@Version
	@Column(nullable = false)
	private Long version;

	@NotEmpty(message = "message.validation.owner.empty")
	protected String ownerUsername;

	@NotEmpty(message = "message.validation.content.empty")
	@Convert(converter = CryptoConverter.class)
	@Column(length = 1)
	protected String content;

	@Column(nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date creationDate;

	protected AbstractMessageEntity() {
		this.creationDate = DateFactory.now();
	}

	@Override
	public String getContent() {
		return content;
	}

	@Override
	public String getOwnerUsername() {
		return ownerUsername;
	}

	@Override
	public Date getCreationDate() {
		return creationDate;
	}
}
