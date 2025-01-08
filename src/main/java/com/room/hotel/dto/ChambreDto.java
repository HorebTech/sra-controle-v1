package com.room.hotel.dto;

import com.room.hotel.response.CategorieResponse;
import com.room.hotel.response.StatutResponse;
import lombok.Data;

import java.util.UUID;

@Data
public class ChambreDto {
    private UUID id;
    private String numero;
    private String localisation;
    private CategorieResponse categorie;
    private StatutResponse statut;
}
