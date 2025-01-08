package com.room.hotel.mapper;

import com.room.hotel.dto.TacheChoisieDto;
import com.room.hotel.model.TacheChoisie;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface TacheChoisieMapper {
    TacheChoisieDto toDto(TacheChoisie tacheChoisie);

    @Mapping(target = "creationDate", ignore = true)
    @Mapping(target = "modificationDate", ignore = true)
    @Mapping(target = "passe", ignore = true)
    @Mapping(target = "tache", ignore = true)
    TacheChoisie toEntity(TacheChoisieDto tacheChoisieDto);

}
