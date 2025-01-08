package com.room.hotel.dto;

import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

import com.room.hotel.response.NettoyageChoisieResponse;
import com.room.hotel.response.ObjetResponse;
import com.room.hotel.response.PanneResponse;
import com.room.hotel.response.SalleResponse;

import lombok.Data;

@Data
public class SalleChoisieDto {
    private UUID id;
    private SalleResponse salle;
    private String duree;
    private LocalTime heureDebut;
    private LocalTime heureFin;
    private List<PanneResponse> panne;
    private List<ObjetResponse> objet;
    private List<NettoyageChoisieResponse> nettoyageChoisie;
}
