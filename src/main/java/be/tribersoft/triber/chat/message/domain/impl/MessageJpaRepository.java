package be.tribersoft.triber.chat.message.domain.impl;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageJpaRepository extends JpaRepository<MessageEntity, String> {

}
