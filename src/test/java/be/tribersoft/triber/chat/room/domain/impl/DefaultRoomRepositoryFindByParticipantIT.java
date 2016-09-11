package be.tribersoft.triber.chat.room.domain.impl;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Set;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import be.tribersoft.triber.chat.Application;

@RunWith(SpringJUnit4ClassRunner.class)
@TestPropertySource(value = "classpath:/application-test.properties")
@SpringBootTest(classes = Application.class, webEnvironment = WebEnvironment.RANDOM_PORT)
@Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:db/clean.sql")
public class DefaultRoomRepositoryFindByParticipantIT {
	@Inject
	private DefaultRoomRepository defaultRoomRepository;

	@Before
	public void setUp() {
		RoomEntity roomEntity1 = new RoomEntity("owner", "name");
		roomEntity1.addParticipant("participant");
		RoomEntity roomEntity2 = new RoomEntity("owner", "name2");
		roomEntity2.addParticipant("participant2");
		RoomEntity roomEntity3 = new RoomEntity("owner", "name3");
		roomEntity3.addParticipant("participant");
		defaultRoomRepository.save(roomEntity1);
		defaultRoomRepository.save(roomEntity2);
		defaultRoomRepository.save(roomEntity3);
	}

	@Test
	public void returnsRoomsWhereUserIsParticipant() {
		Set<RoomEntity> result = defaultRoomRepository.findByParticipant("participant");

		assertThat(result).hasSize(2);
		assertThat(assertContains(result, "name")).isTrue();
		assertThat(assertContains(result, "name3")).isTrue();
	}

	private boolean assertContains(Set<RoomEntity> rooms, String name) {
		return rooms.stream().filter(room -> room.getName().equals(name)).count() != 0;
	}
}
