package be.tribersoft.triber.chat.domain.user.impl;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Version;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import be.tribersoft.triber.chat.domain.user.api.Role;
import be.tribersoft.triber.chat.domain.user.api.User;

@Entity(name = "triberUser")
public class UserEntity implements User {

	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid2")
	@Column(name = "id", unique = true, nullable = false)
	private String id;

	@Version
	@Column(nullable = false)
	@NotEmpty
	private Long version;

	@Column(nullable = false, length = 256)
	@NotEmpty
	private String username;

	@Column(nullable = false, length = 256)
	@NotEmpty
	private String password;

	@Column(nullable = false, length = 512)
	@Email
	@NotEmpty
	private String email;

	@NotEmpty
	@ElementCollection(fetch = FetchType.EAGER)
	@Enumerated(EnumType.STRING)
	private Set<Role> roles;

	protected UserEntity() {

	}

	public UserEntity(String username, String password, String email, Set<Role> roles) {
		this.username = username;
		this.password = password;
		this.email = email;
		this.roles = roles;
	}

	public String getEmail() {
		return email;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public Set<Role> getRoles() {
		return roles;
	}

	@Override
	public String getUsername() {
		return username;
	}
}
