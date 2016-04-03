package be.tribersoft.triber.chat.security;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import be.tribersoft.triber.chat.user.domain.api.Role;

public class SecurityAuthorityConstructorTest {

	@Test
	public void constructsCorrectly() {
		SecurityAuthority result = new SecurityAuthority(Role.ROLE_ADMIN);

		assertThat(result.getAuthority()).isEqualTo(Role.ROLE_ADMIN.name());
	}

}
