package org.civilis.homelab.messageboxapi.mapping;

import org.civilis.homelab.messageboxapi.model.Message;
import org.civilis.homelab.messageboxapi.persistence.entity.MessageEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(uses = MappingUtil.class)
public interface MessageMapper {

    MessageMapper MAPPER = Mappers.getMapper(MessageMapper.class);

    @Mapping(target = "kind", source = "jsonModel.kind", qualifiedByName = "SanitizeField")
    @Mapping(target = "sender", source = "jsonModel.sender", qualifiedByName = "SanitizeField")
    @Mapping(target = "recipient", source = "jsonModel.recipient", qualifiedByName = "SanitizeField")
    MessageEntity convertSanitizedJsonModelToEntity(Message jsonModel);

    MessageEntity convertJsonModelToEntity(Message jsonModel);

    Message convertEntityToJsonModel(MessageEntity entity);

}
