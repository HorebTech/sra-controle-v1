package com.room.hotel.mapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.room.hotel.dto.SalleChoisieDto;
import com.room.hotel.model.SalleChoisie;

@Mapper(componentModel = "spring")
public interface SalleChoisieMapper {
    SalleChoisieDto toDto(SalleChoisie salleChoisie);

    @Mapping(target = "creationDate", ignore = true)
    @Mapping(target = "modificationDate", ignore = true)
    @Mapping(target = "passe", ignore = true)
    @Mapping(target = "salle", ignore = true)
    @Mapping(target = "objet", ignore = true)
    @Mapping(target = "panne", ignore = true)
    @Mapping(target = "nettoyageChoisie", ignore = true)
    SalleChoisie toEntity(SalleChoisieDto salleChoisieDto);
}
