package com.room.hotel.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.room.hotel.dto.PasseDto;
import com.room.hotel.dto.SalleDto;
import com.room.hotel.model.Passe;
import com.room.hotel.model.Salle;

@Mapper(componentModel = "spring")
public interface SalleMapper {

    SalleDto toDto(Salle salle);

    @Mapping(target = "creationDate", ignore = true)
    @Mapping(target = "statut", ignore = true)
    @Mapping(target = "modificationDate", ignore = true)
    @Mapping(target = "salleChoisie", ignore = true)
    Salle toEntity(SalleDto salleDto);

    @Mapping(target = "gouvernante", ignore = true)
    PasseDto toDto(Passe passe);
    @Mapping(target = "creationDate", ignore = true)
    @Mapping(target = "agent", ignore = true)
    @Mapping(target = "statut", ignore = true)
    @Mapping(target = "modificationDate", ignore = true)
    @Mapping(target = "salleChoisie", ignore = true)
    @Mapping(target = "chambreChoisie", ignore = true)
    @Mapping(target = "nettoyageChoisie", ignore = true)
    @Mapping(target = "tacheChoisie", ignore = true)
    @Mapping(target = "gouvernante", ignore = true)
    Passe toEntity(PasseDto passeDto);

}