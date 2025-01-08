package com.room.hotel.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.room.hotel.dto.NettoyageChoisieDto;
import com.room.hotel.model.NettoyageChoisie;


@Mapper(componentModel = "spring")
public interface NettoyageChoisieMapper {
    NettoyageChoisieDto toDto(NettoyageChoisie nettoyageChoisie);

    @Mapping(target = "creationDate", ignore = true)
    @Mapping(target = "modificationDate", ignore = true)
    @Mapping(target = "passe", ignore = true)
    @Mapping(target = "nettoyage", ignore = true)
    @Mapping(target = "chambreChoisie", ignore = true)
    @Mapping(target = "salleChoisie", ignore = true)
    NettoyageChoisie toEntity(NettoyageChoisieDto nettoyageChoisieDto);
}
