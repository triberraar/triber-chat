package be.tribersoft.triber.chat.user.domain.impl;

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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import be.tribersoft.triber.chat.user.domain.api.CanNotActivateUserException;
import be.tribersoft.triber.chat.user.domain.api.Role;
import be.tribersoft.triber.chat.user.domain.api.User;

@Entity(name = "triberUser")
public class UserEntity implements User {

	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid2")
	@Column(name = "id", unique = true, nullable = false)
	private String id;

	@Version
	@Column(nullable = false)
	private Long version;

	@Column(nullable = false, length = 256)
	private String username;

	@Column(nullable = false, length = 256)
	private String password;

	@Column(nullable = false, length = 512)
	private String email;

	@ElementCollection(fetch = FetchType.EAGER)
	@Enumerated(EnumType.STRING)
	private Set<Role> roles;

	@Column(nullable = false)
	private boolean activated;

	@Column(nullable = false)
	private boolean validated;

	protected UserEntity() {

	}

	public UserEntity(String username, String password, String email, Set<Role> roles) {
		this.username = username;
		this.password = new BCryptPasswordEncoder().encode(password);
		this.email = email;
		this.roles = roles;
		this.activated = false;
		this.validated = false;
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
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

	public void activate(String password) {
		if (new BCryptPasswordEncoder().matches(password, this.password)) {
			this.activated = true;
		} else {
			throw new CanNotActivateUserException();
		}
	}

	public void validate() {
		this.validated = true;
	}

	public void changePassword(String password) {
		this.password = new BCryptPasswordEncoder().encode(password);
	}

	@Override
	public boolean isValidated() {
		return validated;
	}

	@Override
	public boolean isActivated() {
		return activated;
	}

	@Override
	public String getUsernameLowerCase() {
		return username.toLowerCase();
	}
}
