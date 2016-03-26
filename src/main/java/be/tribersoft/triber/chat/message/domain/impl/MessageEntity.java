package be.tribersoft.triber.chat.message.domain.impl;

import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.NotEmpty;

import be.tribersoft.triber.chat.common.DateFactory;
import be.tribersoft.triber.chat.common.jpa.CryptoConverter;
import be.tribersoft.triber.chat.message.domain.api.Message;

@Entity(name = "message")
public class MessageEntity implements Message {
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid2")
	@Column(name = "id", unique = true, nullable = false)
	private String id;

	@Version
	@Column(nullable = false)
	private Long version;

	@NotEmpty(message = "message.validation.owner.empty")
	private String ownerUsername;

	@NotEmpty(message = "message.validation.content.empty")
	@Convert(converter = CryptoConverter.class)
	@Column(length = 1)
	private String content;

	@Column(nullable = false)
	private boolean publicAccessible;

	@ElementCollection(fetch = FetchType.EAGER)
	private Set<String> accessibleBy;

	@Column(nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date creationDate;

	protected MessageEntity() {

	}

	public MessageEntity(String ownerUsername, String content, boolean publicAccessible, String... accessibleBy) {
		if (!publicAccessible && accessibleBy.length == 0) {
			throw new IllegalArgumentException("message.validation.private.message.no.accessible.by");
		}
		if (publicAccessible && accessibleBy.length != 0) {
			throw new IllegalArgumentException("message.validation.public.message.accessible.by");

		}
		this.ownerUsername = ownerUsername;
		this.content = content;
		this.publicAccessible = publicAccessible;
		this.accessibleBy = new HashSet<>(Arrays.asList(accessibleBy));
		this.creationDate = DateFactory.now();
	}

	public boolean isPublicAccessible() {
		return publicAccessible;
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
