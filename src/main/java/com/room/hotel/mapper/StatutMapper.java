package com.room.hotel.mapper;

import com.room.hotel.dto.StatutDto;
import com.room.hotel.model.Statut;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface StatutMapper {
    StatutDto toDto(Statut statut);

    @Mapping(target = "creationDate", ignore = true)
    @Mapping(target = "modificationDate", ignore = true)
    Statut toEntity(StatutDto statutDto);

}