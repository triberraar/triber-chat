package be.tribersoft.triber.chat.room.domain.impl;

import javax.inject.Inject;
import javax.inject.Named;

@Named
public class DefaultRoomRepository {

	@Inject
	private RoomJpaRepository roomJpaRepository;

	public void save(RoomEntity roomEntity) {
		roomJpaRepository.save(roomEntity);
	}
}
