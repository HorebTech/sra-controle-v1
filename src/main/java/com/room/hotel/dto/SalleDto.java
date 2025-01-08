package com.room.hotel.dto;

import com.room.hotel.response.StatutResponse;
import lombok.Data;

import java.util.UUID;

@Data
public class SalleDto {
    private UUID id;
    private String numero;
    private String description;
    private StatutResponse statut;
}
