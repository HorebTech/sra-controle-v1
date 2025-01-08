package com.room.hotel.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class NettoyageChoisieDto {
    private UUID id;
    private NettoyageDto nettoyage;
}
