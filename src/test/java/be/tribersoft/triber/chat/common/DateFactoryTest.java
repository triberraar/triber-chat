package be.tribersoft.triber.chat.common;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;

import org.junit.Test;

public class DateFactoryTest {

	@Test
	public void allowsToFixateDate() {
		Date now = new Date(1);

		assertThat(DateFactory.now()).isNotEqualTo(now);

		DateFactory.fixate(now);

		assertThat(DateFactory.now()).isEqualTo(now);

		DateFactory.release();

		assertThat(DateFactory.now()).isNotEqualTo(now);
	}
}
