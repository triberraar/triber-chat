package be.tribersoft.triber.chat.user.domain.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.google.common.collect.Sets;

import be.tribersoft.triber.chat.user.domain.api.Role;
import be.tribersoft.triber.chat.user.domain.api.UserMessage;

@RunWith(MockitoJUnitRunner.class)
public class UserFactoryCreateTest {

	private static final String EMAIL = "email@email";
	private static final String PASSWORD = "password";
	private static final String USERNAME = "username";

	private UserFactory userFactory = new UserFactory();

	@Mock
	private UserMessage userMessage;

	@Before
	public void setUp() {
		when(userMessage.getEmail()).thenReturn(EMAIL);
		when(userMessage.getPassword()).thenReturn(PASSWORD);
		when(userMessage.getUsername()).thenReturn(USERNAME);
	}

	@Test
	public void createsAUserEntity() {
		UserEntity userEntity = userFactory.create(userMessage);

		assertThat(userEntity.getEmail()).isEqualTo(EMAIL);
		assertThat(new BCryptPasswordEncoder().matches(PASSWORD, userEntity.getPassword())).isTrue();
		assertThat(userEntity.getRoles()).isEqualTo(Sets.newHashSet(Role.ROLE_USER));
		assertThat(userEntity.getUsername()).isEqualTo(USERNAME);
		assertThat(userEntity.isActivated()).isFalse();
		assertThat(userEntity.isValidated()).isFalse();
	}
}
