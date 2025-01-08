package com.room.hotel.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.room.hotel.dto.TacheDto;
import com.room.hotel.model.Tache;

@Mapper(componentModel = "spring", uses = {CategorieMapper.class})
public interface TacheMapper {
    @Mapping(source = "categorie", target = "categorie")
    @Mapping(source = "sous_categorie", target = "sous_categorie")
    TacheDto toDto(Tache Tache);

    @Mapping(target = "categorie", ignore = true)
    @Mapping(target = "sous_categorie", ignore = true)
    @Mapping(target = "creationDate", ignore = true)
    @Mapping(target = "modificationDate", ignore = true)
    Tache toEntity(TacheDto TacheDto);

}