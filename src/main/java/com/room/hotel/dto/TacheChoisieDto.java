package com.room.hotel.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class TacheChoisieDto {
    private UUID id;
    private TacheDto tache;
}
