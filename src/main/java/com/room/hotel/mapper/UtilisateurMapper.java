package com.room.hotel.mapper;

import com.room.hotel.dto.UtilisateurDto;
import com.room.hotel.model.Utilisateur;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UtilisateurMapper {
    UtilisateurDto toDto(Utilisateur utilisateur);

    @Mapping(target = "creationDate", ignore = true)
    @Mapping(target = "modificationDate", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "authorities", ignore = true)
    Utilisateur toEntity(UtilisateurDto utilisateurDto);
}