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

public class PrivateMessageEntityValidationTest {

	private static Validator validator;

	@BeforeClass
	public static void setUp() {

		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}

	@Test
	public void failsWhenContentEmpty() {
		PrivateMessageEntity message = new PrivateMessageEntity("", "touser", "fromuser");

		Set<ConstraintViolation<PrivateMessageEntity>> violations = validator.validate(message);
		assertThat(violations).hasSize(1);
		assertThat(violations.iterator().next().getMessage()).isEqualTo("message.validation.content.empty");
	}

	@Test
	public void failsWhenContentLongerThen2048() {
		PrivateMessageEntity message = new PrivateMessageEntity(StringUtils.leftPad("", 2049, "a"), "touser", "fromuser");

		Set<ConstraintViolation<PrivateMessageEntity>> violations = validator.validate(message);
		assertThat(violations).hasSize(1);
		assertThat(violations.iterator().next().getMessage()).isEqualTo("message.validation.content.length");

	}

	@Test
	public void failsWhenToEmpty() {
		PrivateMessageEntity message = new PrivateMessageEntity("content", "", "fromuser");

		Set<ConstraintViolation<PrivateMessageEntity>> violations = validator.validate(message);
		assertThat(violations).hasSize(2);
		assertThat(assertViolationsContains(violations, "message.validation.to.empty")).isTrue();
		assertThat(assertViolationsContains(violations, "message.validation.to.length")).isTrue();
	}

	@Test
	public void failsWhenToShorterThen4() {
		PrivateMessageEntity message = new PrivateMessageEntity("content", "123", "fromuser");

		Set<ConstraintViolation<PrivateMessageEntity>> violations = validator.validate(message);
		assertThat(violations).hasSize(1);
		assertThat(violations.iterator().next().getMessage()).isEqualTo("message.validation.to.length");

	}

	@Test
	public void failsWhenToLongerThen256() {
		PrivateMessageEntity message = new PrivateMessageEntity("content", StringUtils.leftPad("", 257, "a"), "fromuser");

		Set<ConstraintViolation<PrivateMessageEntity>> violations = validator.validate(message);
		assertThat(violations).hasSize(1);
		assertThat(violations.iterator().next().getMessage()).isEqualTo("message.validation.to.length");

	}

	@Test
	public void failsWhenFromEmpty() {
		PrivateMessageEntity message = new PrivateMessageEntity("content", "touser", "");

		Set<ConstraintViolation<PrivateMessageEntity>> violations = validator.validate(message);
		assertThat(violations).hasSize(2);
		assertThat(assertViolationsContains(violations, "message.validation.from.empty")).isTrue();
		assertThat(assertViolationsContains(violations, "message.validation.from.length")).isTrue();
	}

	@Test
	public void failsWhenFromShorterThen4() {
		PrivateMessageEntity message = new PrivateMessageEntity("content", "touser", "123");

		Set<ConstraintViolation<PrivateMessageEntity>> violations = validator.validate(message);
		assertThat(violations).hasSize(1);
		assertThat(violations.iterator().next().getMessage()).isEqualTo("message.validation.from.length");

	}

	@Test
	public void failsWhenFromLongerThen256() {
		PrivateMessageEntity message = new PrivateMessageEntity("content", "touser", StringUtils.leftPad("", 257, "a"));

		Set<ConstraintViolation<PrivateMessageEntity>> violations = validator.validate(message);
		assertThat(violations).hasSize(1);
		assertThat(violations.iterator().next().getMessage()).isEqualTo("message.validation.from.length");

	}

	@Test
	public void failsWhenCreationDateEmpty() {
		PrivateMessageEntity message = new PrivateMessageEntity("content", "touser", "fromuser");
		Whitebox.setInternalState(message, "creationDate", null);

		Set<ConstraintViolation<PrivateMessageEntity>> violations = validator.validate(message);
		assertThat(violations).hasSize(1);
		assertThat(violations.iterator().next().getMessage()).isEqualTo("message.validation.creation.date.null");

	}

	@Test
	public void succeedsOtherwise() {
		PrivateMessageEntity message = new PrivateMessageEntity(StringUtils.leftPad("", 2048, "a"), StringUtils.leftPad("", 256, "a"), StringUtils.leftPad("", 256, "a"));

		Set<ConstraintViolation<PrivateMessageEntity>> violations = validator.validate(message);
		assertThat(violations).isEmpty();

	}

	private boolean assertViolationsContains(Set<ConstraintViolation<PrivateMessageEntity>> violations, String message) {
		for (ConstraintViolation<PrivateMessageEntity> constraintViolation : violations) {
			if (constraintViolation.getMessage().equals(message)) {
				return true;
			}
		}
		return false;
	}

}
