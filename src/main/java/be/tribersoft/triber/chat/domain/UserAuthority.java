package be.tribersoft.triber.chat.domain;

import org.springframework.security.core.GrantedAuthority;

public class UserAuthority implements GrantedAuthority {

	@Override
	public String getAuthority() {
		return "USER";
	}

}
