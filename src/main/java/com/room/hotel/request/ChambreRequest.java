package com.room.hotel.request;

import lombok.Data;

@Data
public class ChambreRequest {
    private String numero;
    private String localisation;
    private String categorie;
    private String statut;
}
