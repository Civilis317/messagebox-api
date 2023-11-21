package org.civilis.homelab.messageboxapi.persistence.repository;

import org.civilis.homelab.messageboxapi.persistence.entity.HeaderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

import java.util.Optional;

public interface HeaderRepository extends JpaRepository<HeaderEntity, Long>, QueryByExampleExecutor<HeaderEntity> {

    Optional<HeaderEntity> findByProductIdAndUsername(String productId, String username);
}
