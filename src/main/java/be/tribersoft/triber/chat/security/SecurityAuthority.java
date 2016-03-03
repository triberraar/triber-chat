package be.tribersoft.triber.chat.security;

import org.springframework.security.core.GrantedAuthority;

import be.tribersoft.triber.chat.domain.user.api.Role;

public class SecurityAuthority implements GrantedAuthority {

	private Role role;

	public SecurityAuthority(Role role) {
		this.role = role;
	}

	@Override
	public String getAuthority() {
		return role.name();
	}

}
