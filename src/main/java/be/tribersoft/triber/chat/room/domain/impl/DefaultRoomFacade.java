package be.tribersoft.triber.chat.room.domain.impl;

import javax.inject.Inject;
import javax.inject.Named;

import be.tribersoft.triber.chat.room.domain.api.RoomFacade;

@Named
public class DefaultRoomFacade implements RoomFacade {

	@Inject
	private RoomFactory roomFactory;

	@Inject
	private DefaultRoomRepository defaultRoomRepository;

	@Override
	public RoomEntity create(String owner, String name) {
		RoomEntity roomEntity = roomFactory.create(owner, name);

		defaultRoomRepository.save(roomEntity);

		return roomEntity;
	}

}
