package be.tribersoft.triber.chat.room.domain.impl;

import java.util.HashSet;
import java.util.Set;

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

	@Override
	public RoomEntity invite(String roomId, String participant) {
		RoomEntity roomEntity = defaultRoomRepository.getById(roomId);

		roomEntity.addParticipant(participant);

		return roomEntity;
	}

	@Override
	public Set<RoomEntity> removeUserFromAllRooms(String username) {
		Set<RoomEntity> ownedRooms = defaultRoomRepository.findByOwner(username);
		ownedRooms.forEach(ownedRoom -> ownedRoom.inactivate());

		Set<RoomEntity> participatedRooms = defaultRoomRepository.findByParticipant(username);
		participatedRooms.forEach(particpatedRoom -> particpatedRoom.removeParticipant(username));

		Set<RoomEntity> returnValue = new HashSet<>();
		returnValue.addAll(ownedRooms);
		returnValue.addAll(participatedRooms);
		return returnValue;
	}

}
