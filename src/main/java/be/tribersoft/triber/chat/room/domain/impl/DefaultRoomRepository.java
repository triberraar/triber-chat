package be.tribersoft.triber.chat.room.domain.impl;

import javax.inject.Inject;
import javax.inject.Named;

import be.tribersoft.triber.chat.room.domain.api.RoomNotFoundException;
import be.tribersoft.triber.chat.room.domain.api.RoomRepository;

@Named
public class DefaultRoomRepository implements RoomRepository {

	@Inject
	private RoomJpaRepository roomJpaRepository;

	public void save(RoomEntity roomEntity) {
		roomJpaRepository.save(roomEntity);
	}

	@Override
	public RoomEntity getById(String id) {
		RoomEntity roomEntity = roomJpaRepository.findOne(id);
		if (roomEntity == null) {
			throw new RoomNotFoundException();
		}
		return roomEntity;
	}
}
