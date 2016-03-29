package be.tribersoft.triber.chat.message.domain.impl;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.apache.commons.lang3.StringUtils;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.internal.util.reflection.Whitebox;

public class PublicMessageEntityValidationTest {

	private static Validator validator;

	@BeforeClass
	public static void setUp() {

		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}

	@Test
	public void failsWhenContentEmpty() {
		PublicMessageEntity message = new PublicMessageEntity("username", "");

		Set<ConstraintViolation<PublicMessageEntity>> violations = validator.validate(message);
		assertThat(violations).hasSize(1);
		assertThat(violations.iterator().next().getMessage()).isEqualTo("message.validation.content.empty");
	}

	@Test
	public void failsWhenContentLongerThen2048() {
		PublicMessageEntity message = new PublicMessageEntity("username", StringUtils.leftPad("", 2049, "a"));

		Set<ConstraintViolation<PublicMessageEntity>> violations = validator.validate(message);
		assertThat(violations).hasSize(1);
		assertThat(violations.iterator().next().getMessage()).isEqualTo("message.validation.content.length");

	}

	@Test
	public void failsWhenOwnerUsernameEmpty() {
		PublicMessageEntity message = new PublicMessageEntity("", "content");

		Set<ConstraintViolation<PublicMessageEntity>> violations = validator.validate(message);
		assertThat(violations).hasSize(2);
		assertThat(assertViolationsContains(violations, "message.validation.owner.username.empty")).isTrue();
		assertThat(assertViolationsContains(violations, "message.validation.owner.username.length")).isTrue();

	}

	@Test
	public void failsWhenOwnerUsernameShorterThen4() {
		PublicMessageEntity message = new PublicMessageEntity("123", "content");

		Set<ConstraintViolation<PublicMessageEntity>> violations = validator.validate(message);
		assertThat(violations).hasSize(1);
		assertThat(violations.iterator().next().getMessage()).isEqualTo("message.validation.owner.username.length");

	}

	@Test
	public void failsWhenOwnerUsernameLongerThen256() {
		PublicMessageEntity message = new PublicMessageEntity(StringUtils.leftPad("", 257, "a"), "content");

		Set<ConstraintViolation<PublicMessageEntity>> violations = validator.validate(message);
		assertThat(violations).hasSize(1);
		assertThat(violations.iterator().next().getMessage()).isEqualTo("message.validation.owner.username.length");

	}

	@Test
	public void failsWhenCreationDateEmpty() {
		PublicMessageEntity message = new PublicMessageEntity("username", "content");
		Whitebox.setInternalState(message, "creationDate", null);

		Set<ConstraintViolation<PublicMessageEntity>> violations = validator.validate(message);
		assertThat(violations).hasSize(1);
		assertThat(violations.iterator().next().getMessage()).isEqualTo("message.validation.creation.date.null");

	}

	@Test
	public void succeedsOtherwise() {
		PublicMessageEntity message = new PublicMessageEntity(StringUtils.leftPad("", 256, "a"), StringUtils.leftPad("", 2048, "a"));

		Set<ConstraintViolation<PublicMessageEntity>> violations = validator.validate(message);
		assertThat(violations).isEmpty();

	}

	private boolean assertViolationsContains(Set<ConstraintViolation<PublicMessageEntity>> violations, String message) {
		for (ConstraintViolation<PublicMessageEntity> constraintViolation : violations) {
			if (constraintViolation.getMessage().equals(message)) {
				return true;
			}
		}
		return false;
	}

}
