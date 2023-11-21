package org.civilis.homelab.messageboxapi.persistence.repository;

import org.civilis.homelab.messageboxapi.persistence.entity.MessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

public interface MessageRepository extends JpaRepository<MessageEntity, Long>, QueryByExampleExecutor<MessageEntity> {
}
