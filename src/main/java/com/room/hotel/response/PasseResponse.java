package com.room.hotel.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PasseResponse {
    private Long id;
    private UtilisateurResponse agent;
    private UtilisateurResponse gouvernante;
    private StatutResponse statut;
    private String commentaire;
    private String dateNettoyage;
}
