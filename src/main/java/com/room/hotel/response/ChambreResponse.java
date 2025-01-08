package com.room.hotel.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ChambreResponse {
    private String numero;
    private String localisation;
    private CategorieResponse categorie;
    private StatutResponse statut;
}
