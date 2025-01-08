package com.room.hotel.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.room.hotel.dto.NettoyageDto;
import com.room.hotel.model.Nettoyage;

@Mapper(componentModel = "spring")
public interface NettoyageMapper {
    NettoyageDto toDto(Nettoyage nettoyage);

    @Mapping(target = "categorie", ignore = true)
    @Mapping(target = "creationDate", ignore = true)
    @Mapping(target = "modificationDate", ignore = true)
    Nettoyage toEntity(NettoyageDto nettoyageDto);
}
