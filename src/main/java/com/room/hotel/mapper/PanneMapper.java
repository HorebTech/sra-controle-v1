package com.room.hotel.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.room.hotel.dto.PanneDto;
import com.room.hotel.model.Panne;

@Mapper(componentModel = "spring")
public interface PanneMapper {
    PanneDto toDto(Panne panne);

    @Mapping(target = "modificationDate", ignore = true)
    @Mapping(target = "chambreChoisie", ignore = true)
    @Mapping(target = "salleChoisie", ignore = true)
    @Mapping(target = "statut", ignore = true)
    @Mapping(target = "marqueEquipement", ignore = true)
    Panne toEntity(PanneDto panneDto);

}
