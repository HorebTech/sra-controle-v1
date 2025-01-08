package com.room.hotel.request;

import lombok.Data;

@Data
public class PasseRequest {
    private String agent;
    private String dateNettoyage;
    private String statut;
    private String commentaire;
}
