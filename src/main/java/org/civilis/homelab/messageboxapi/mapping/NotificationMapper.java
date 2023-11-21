package org.civilis.homelab.messageboxapi.mapping;

import org.civilis.homelab.messageboxapi.model.Notification;
import org.civilis.homelab.messageboxapi.persistence.entity.NotificationEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;


@Mapper
public interface NotificationMapper {
    NotificationMapper MAPPER = Mappers.getMapper(NotificationMapper.class);

    NotificationEntity convertJsonModelToEntity(Notification jsonModel);

    Notification convertEntityToJsonModel(NotificationEntity entity);
}
