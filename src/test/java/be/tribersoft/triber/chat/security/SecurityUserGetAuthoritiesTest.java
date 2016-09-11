package be.tribersoft.triber.chat.security;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.google.common.collect.Sets;

import be.tribersoft.triber.chat.user.domain.api.Role;
import be.tribersoft.triber.chat.user.domain.api.User;

@RunWith(MockitoJUnitRunner.class)
public class SecurityUserGetAuthoritiesTest {

	@Mock
	private User user;

	@Before
	public void setUp() {
		when(user.getRoles()).thenReturn(Sets.newHashSet(Role.ROLE_USER));
	}

	@Test
	public void returnsAllRoles() {
		SecurityUser result = new SecurityUser(user);

		assertThat(result.getAuthorities()).hasSize(1);
		assertThat(((SecurityAuthority) result.getAuthorities().iterator().next()).getAuthority()).isEqualTo(Role.ROLE_USER.name());
	}

}
