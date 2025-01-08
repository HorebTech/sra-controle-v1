package com.room.hotel.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.room.hotel.dto.PasseDto;
import com.room.hotel.model.Passe;

@Mapper(componentModel = "spring", uses = {UtilisateurMapper.class, StatutMapper.class})
public interface PasseMapper {
    @Mapping(source = "agent", target = "agent")
    @Mapping(source = "gouvernante", target = "gouvernante")
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
