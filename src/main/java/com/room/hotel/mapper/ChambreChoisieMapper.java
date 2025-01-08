package com.room.hotel.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.room.hotel.dto.ChambreChoisieDto;
import com.room.hotel.model.ChambreChoisie;

@Mapper(componentModel = "spring")
public interface ChambreChoisieMapper {
    ChambreChoisieDto toDto(ChambreChoisie chambreChoisie);

    @Mapping(target = "creationDate", ignore = true)
    @Mapping(target = "modificationDate", ignore = true)
    @Mapping(target = "passe", ignore = true)
    @Mapping(target = "chambre", ignore = true)
    @Mapping(target = "objet", ignore = true)
    @Mapping(target = "panne", ignore = true)
    @Mapping(target = "nettoyageChoisie", ignore = true)
    ChambreChoisie toEntity(ChambreChoisieDto chambreChoisieDto);
}
