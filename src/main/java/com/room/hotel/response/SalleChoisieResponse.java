package com.room.hotel.response;

import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SalleChoisieResponse {
    private UUID id;
    private SalleResponse salle;
    private String duree;
    private LocalTime heureDebut;
    private LocalTime heureFin;
    private List<PanneResponse> panne;
    private List<ObjetResponse> objet;
    private List<NettoyageChoisieResponse> nettoyageChoisie;

}
