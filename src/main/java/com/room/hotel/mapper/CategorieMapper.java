package com.room.hotel.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.room.hotel.dto.CategorieDto;
import com.room.hotel.model.Categorie;

@Mapper(componentModel = "spring")
public interface CategorieMapper {
    CategorieDto toDto(Categorie categorie);

    @Mapping(target = "creationDate", ignore = true)
    @Mapping(target = "modificationDate", ignore = true)
    Categorie toEntity(CategorieDto categorieDto);

}