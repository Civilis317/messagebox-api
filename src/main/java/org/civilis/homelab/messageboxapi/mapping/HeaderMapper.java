package org.civilis.homelab.messageboxapi.mapping;

import org.civilis.homelab.messageboxapi.model.Header;
import org.civilis.homelab.messageboxapi.persistence.entity.HeaderEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(uses = MappingUtil.class)
public interface HeaderMapper {

    HeaderMapper MAPPER = Mappers.getMapper(HeaderMapper.class);

    @Mapping(target = "username", source = "jsonModel.username", qualifiedByName = "SanitizeField")
    HeaderEntity convertSanitizedJsonModelToEntity(Header jsonModel);

    HeaderEntity convertJsonModelToEntity(Header jsonModel);

    Header convertEntityToJsonModel(HeaderEntity entity);

}
