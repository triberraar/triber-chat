package be.tribersoft.triber.chat.room.domain.impl;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomJpaRepository extends JpaRepository<RoomEntity, String> {

}
