package be.tribersoft.triber.chat.reset.password.domain.impl;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.time.DateUtils;
import org.hibernate.annotations.GenericGenerator;

import be.tribersoft.triber.chat.common.DateFactory;
import be.tribersoft.triber.chat.reset.password.domain.api.ResetPassword;

@Entity(name = "resetPassword")
public class ResetPasswordEntity implements ResetPassword {

	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid2")
	@Column(name = "id", unique = true, nullable = false)
	private String id;

	@NotNull
	private String userId;

	@Temporal(TemporalType.TIMESTAMP)
	@NotNull
	private Date expireDate;

	protected ResetPasswordEntity() {

	}

	public ResetPasswordEntity(String userId) {
		this.userId = userId;
		this.expireDate = DateUtils.addDays(DateFactory.create(), 7);
	}

	public boolean isValid() {
		return expireDate.after(DateFactory.create());
	}

	@Override
	public String getUserId() {
		return userId;
	}

	@Override
	public String getId() {
		return id;
	}
}
