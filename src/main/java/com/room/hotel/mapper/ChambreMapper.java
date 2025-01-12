package com.room.hotel.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.room.hotel.dto.ChambreDto;
import com.room.hotel.dto.PasseDto;
import com.room.hotel.model.Chambre;
import com.room.hotel.model.Passe;

@Mapper(componentModel = "spring")
public interface ChambreMapper {

    ChambreDto toDto(Chambre chambre);

    @Mapping(target = "chambreChoisie", ignore = true)
    @Mapping(target = "creationDate", ignore = true)
    @Mapping(target = "modificationDate", ignore = true)
    @Mapping(target = "statut", ignore = true)
    @Mapping(target = "categorie", ignore = true)
    Chambre toEntity(ChambreDto chambreDto);

    @Mapping(target = "gouvernante", ignore = true)
    @Mapping(target = "chambreChoisie", ignore = true)
    @Mapping(target = "salleChoisie", ignore = true)
    PasseDto toDto(Passe passe);
    @Mapping(target = "creationDate", ignore = true)
    @Mapping(target = "modificationDate", ignore = true)
    @Mapping(target = "chambreChoisie", ignore = true)
    @Mapping(target = "salleChoisie", ignore = true)
    @Mapping(target = "nettoyageChoisie", ignore = true)
    @Mapping(target = "tacheChoisie", ignore = true)
    @Mapping(target = "statut", ignore = true)
    @Mapping(target = "agent", ignore = true)
    @Mapping(target = "gouvernante", ignore = true)
    Passe toEntity(PasseDto passeDto);

}