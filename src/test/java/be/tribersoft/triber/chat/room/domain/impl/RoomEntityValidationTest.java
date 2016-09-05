package be.tribersoft.triber.chat.room.domain.impl;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.apache.commons.lang3.StringUtils;
import org.junit.BeforeClass;
import org.junit.Test;

public class RoomEntityValidationTest {

	private static Validator validator;

	@BeforeClass
	public static void setUp() {

		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}

	@Test
	public void failsWhenNameNull() {
		RoomEntity roomEntity = new RoomEntity("owner", null);

		Set<ConstraintViolation<RoomEntity>> violations = validator.validate(roomEntity);

		assertThat(violations).hasSize(1);
		assertThat(violations.iterator().next().getMessage()).isEqualTo("room.validation.name.empty");
	}

	@Test
	public void failsWhenNameEmpty() {
		RoomEntity roomEntity = new RoomEntity("owner", "");

		Set<ConstraintViolation<RoomEntity>> violations = validator.validate(roomEntity);

		assertThat(violations).hasSize(2);
		assertViolationsContains(violations, "room.validation.name.empty");
		assertViolationsContains(violations, "room.validation.name.length");
	}

	@Test
	public void failsWhenNameShorterThen4() {
		RoomEntity roomEntity = new RoomEntity("owner", "avc");

		Set<ConstraintViolation<RoomEntity>> violations = validator.validate(roomEntity);

		assertThat(violations).hasSize(1);
		assertThat(violations.iterator().next().getMessage()).isEqualTo("room.validation.name.length");
	}

	@Test
	public void failsWhenNameLongerThen256() {
		RoomEntity roomEntity = new RoomEntity("owner", StringUtils.leftPad("", 257, "a"));

		Set<ConstraintViolation<RoomEntity>> violations = validator.validate(roomEntity);

		assertThat(violations).hasSize(1);
		assertThat(violations.iterator().next().getMessage()).isEqualTo("room.validation.name.length");
	}

	@Test
	public void failsWhenOwnerNull() {
		RoomEntity roomEntity = new RoomEntity(null, "room name");

		Set<ConstraintViolation<RoomEntity>> violations = validator.validate(roomEntity);

		assertThat(violations).hasSize(1);
		assertThat(violations.iterator().next().getMessage()).isEqualTo("room.validation.owner.empty");
	}

	@Test
	public void failsWhenOwnerEmpty() {
		RoomEntity roomEntity = new RoomEntity("", "room name");

		Set<ConstraintViolation<RoomEntity>> violations = validator.validate(roomEntity);

		assertThat(violations).hasSize(2);
		assertViolationsContains(violations, "room.validation.owner.empty");
		assertViolationsContains(violations, "room.validation.owner.length");
	}

	@Test
	public void failsWhenOwnerLongerThen256() {
		RoomEntity roomEntity = new RoomEntity(StringUtils.leftPad("", 257, "a"), "room name");

		Set<ConstraintViolation<RoomEntity>> violations = validator.validate(roomEntity);

		assertThat(violations).hasSize(1);
		assertThat(violations.iterator().next().getMessage()).isEqualTo("room.validation.owner.length");
	}

	private boolean assertViolationsContains(Set<ConstraintViolation<RoomEntity>> violations, String message) {
		for (ConstraintViolation<RoomEntity> constraintViolation : violations) {
			if (constraintViolation.getMessage().equals(message)) {
				return true;
			}
		}
		return false;
	}
}
