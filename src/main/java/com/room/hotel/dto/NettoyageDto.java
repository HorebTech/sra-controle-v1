package com.room.hotel.dto;

import java.util.UUID;

import com.room.hotel.response.CategorieResponse;

import lombok.Data;

@Data
public class NettoyageDto {
    private UUID id;
    private String action;
    private CategorieResponse categorie;
}
