package org.civilis.homelab.messageboxapi.persistence.repository;

import jakarta.validation.constraints.NotNull;
import org.civilis.homelab.messageboxapi.persistence.entity.NotificationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.query.QueryByExampleExecutor;

public interface NotificationRepository extends JpaRepository<NotificationEntity, Long>, QueryByExampleExecutor<NotificationEntity> {

    @SuppressWarnings("NullableProblems")
    @Modifying
    @Query("delete from NotificationEntity c where c.id = :id")
    void deleteById(@Param("id") @NotNull Long id);
}
