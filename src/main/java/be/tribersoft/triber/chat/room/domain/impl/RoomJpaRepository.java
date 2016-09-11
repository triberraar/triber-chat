package be.tribersoft.triber.chat.room.domain.impl;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

public interface RoomJpaRepository extends JpaRepository<RoomEntity, String>, QueryDslPredicateExecutor<RoomEntity> {

	public Set<RoomEntity> findByOwner(String owner);

}
