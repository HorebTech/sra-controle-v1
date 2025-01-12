package com.room.hotel.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.room.hotel.dto.MarqueDto;
import com.room.hotel.model.Marque;

@Mapper(componentModel = "spring")
public interface MarqueMapper {
    MarqueDto toDto(Marque marque);

    @Mapping(target = "creationDate", ignore = true)
    @Mapping(target = "modificationDate", ignore = true)
    Marque toEntity(MarqueDto marqueDto);

}