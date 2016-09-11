package be.tribersoft.triber.chat.room.domain.impl;

import java.util.Set;

import javax.inject.Inject;
import javax.inject.Named;

import com.google.common.collect.Sets;
import com.querydsl.core.types.dsl.BooleanExpression;

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

	@Override
	public Set<RoomEntity> findByParticipant(String participant) {
		BooleanExpression predicate = QRoomEntity.roomEntity.participants.contains(participant);
		return Sets.newHashSet(roomJpaRepository.findAll(predicate));
	}

	@Override
	public Set<RoomEntity> findByOwner(String owner) {
		return roomJpaRepository.findByOwner(owner);
	}

}
