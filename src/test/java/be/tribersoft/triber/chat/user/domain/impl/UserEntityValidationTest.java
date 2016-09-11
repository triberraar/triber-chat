package be.tribersoft.triber.chat.user.domain.impl;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.apache.commons.lang3.StringUtils;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.internal.util.reflection.Whitebox;

import com.google.common.collect.Sets;

import be.tribersoft.triber.chat.user.domain.api.Role;

public class UserEntityValidationTest {

	private static Validator validator;

	@BeforeClass
	public static void setUp() {

		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}

	@Test
	public void failsWhenUsernameEmpty() {
		UserEntity user = new UserEntity("", "password", "email@host.com", Sets.newHashSet(Role.ROLE_USER));

		Set<ConstraintViolation<UserEntity>> violations = validator.validate(user);
		assertThat(violations).hasSize(2);
		assertThat(assertViolationsContains(violations, "user.validation.username.empty")).isTrue();
		assertThat(assertViolationsContains(violations, "user.validation.username.length")).isTrue();
	}

	@Test
	public void failsWhenUsernameTooShort() {
		UserEntity user = new UserEntity("123", "password", "email@host.com", Sets.newHashSet(Role.ROLE_USER));

		Set<ConstraintViolation<UserEntity>> violations = validator.validate(user);
		assertThat(violations).hasSize(1);
		assertThat(violations.iterator().next().getMessage()).isEqualTo("user.validation.username.length");
	}

	@Test
	public void failsWhenUsernameLongerThen256() {
		UserEntity user = new UserEntity(StringUtils.leftPad("", 257, "a"), "password", "email@host.com", Sets.newHashSet(Role.ROLE_USER));

		Set<ConstraintViolation<UserEntity>> violations = validator.validate(user);
		assertThat(violations).hasSize(1);
		assertThat(violations.iterator().next().getMessage()).isEqualTo("user.validation.username.length");

	}

	@Test
	public void failsWhenPasswordEmpty() {
		UserEntity user = new UserEntity("user", "", "email@host.com", Sets.newHashSet(Role.ROLE_USER));
		Whitebox.setInternalState(user, "password", "");

		Set<ConstraintViolation<UserEntity>> violations = validator.validate(user);
		assertThat(violations).hasSize(2);
		assertThat(assertViolationsContains(violations, "user.validation.password.empty")).isTrue();
		assertThat(assertViolationsContains(violations, "user.validation.password.length")).isTrue();
	}

	@Test
	public void failsWhenPasswordTooShort() {
		UserEntity user = new UserEntity("user", "12345", "email@host.com", Sets.newHashSet(Role.ROLE_USER));
		Whitebox.setInternalState(user, "password", "12345");

		Set<ConstraintViolation<UserEntity>> violations = validator.validate(user);
		assertThat(violations).hasSize(1);
		assertThat(violations.iterator().next().getMessage()).isEqualTo("user.validation.password.length");
	}

	@Test
	public void failsWhenPasswordLongerThen256() {
		UserEntity user = new UserEntity("user", StringUtils.leftPad("", 257, "a"), "email@host.com", Sets.newHashSet(Role.ROLE_USER));
		Whitebox.setInternalState(user, "password", StringUtils.leftPad("", 257, "a"));

		Set<ConstraintViolation<UserEntity>> violations = validator.validate(user);
		assertThat(violations).hasSize(1);
		assertThat(violations.iterator().next().getMessage()).isEqualTo("user.validation.password.length");

	}

	@Test
	public void failsWhenEmailEmpty() {
		UserEntity user = new UserEntity("user", "password", "", Sets.newHashSet(Role.ROLE_USER));

		Set<ConstraintViolation<UserEntity>> violations = validator.validate(user);
		assertThat(violations).hasSize(1);
		assertThat(violations.iterator().next().getMessage()).isEqualTo("user.validation.email.empty");
	}

	@Test
	public void failsWhenEmailNotAValidEmailAddress() {
		UserEntity user = new UserEntity("user", "password", "test", Sets.newHashSet(Role.ROLE_USER));

		Set<ConstraintViolation<UserEntity>> violations = validator.validate(user);
		assertThat(violations).hasSize(1);
		assertThat(violations.iterator().next().getMessage()).isEqualTo("user.validation.email.format");
	}

	@Test
	public void failsWhenEmailNoRole() {
		UserEntity user = new UserEntity("user", "password", "test@test.com", new HashSet<>());

		Set<ConstraintViolation<UserEntity>> violations = validator.validate(user);
		assertThat(violations).hasSize(1);
		assertThat(violations.iterator().next().getMessage()).isEqualTo("user.validation.roles.empty");
	}

	@Test
	public void succeedsOtherwise() {
		UserEntity user = new UserEntity("user", "123456", "test@test.com", Sets.newHashSet(Role.ROLE_USER));

		Set<ConstraintViolation<UserEntity>> violations = validator.validate(user);
		assertThat(violations).isEmpty();
	}

	private boolean assertViolationsContains(Set<ConstraintViolation<UserEntity>> violations, String message) {
		for (ConstraintViolation<UserEntity> constraintViolation : violations) {
			if (constraintViolation.getMessage().equals(message)) {
				return true;
			}
		}
		return false;
	}

}
