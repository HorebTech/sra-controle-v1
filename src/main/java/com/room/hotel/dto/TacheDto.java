package com.room.hotel.dto;
import java.util.UUID;

import lombok.Data;

@Data
public class TacheDto {
    private UUID id;
    private String action;
    private CategorieDto categorie;
    private CategorieDto sous_categorie;

}
