package com.room.hotel.mapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.room.hotel.dto.ObjetDto;
import com.room.hotel.model.Objet;

@Mapper(componentModel = "spring")
public interface ObjetMapper {
    ObjetDto toDto(Objet objet);

    @Mapping(target = "modificationDate", ignore = true)
    @Mapping(target = "chambreChoisie", ignore = true)
    @Mapping(target = "salleChoisie", ignore = true)
    @Mapping(target = "statut", ignore = true)
    Objet toEntity(ObjetDto objetDto);
}