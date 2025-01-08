package com.room.hotel.dto;

import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

import com.room.hotel.response.ChambreResponse;
import com.room.hotel.response.NettoyageChoisieResponse;
import com.room.hotel.response.ObjetResponse;
import com.room.hotel.response.PanneResponse;

import lombok.Data;

@Data
public class ChambreChoisieDto {
    private UUID id;
    private ChambreResponse chambre;
    private String duree;
    private LocalTime heureDebut;
    private LocalTime heureFin;
    private List<PanneResponse> panne;
    private List<ObjetResponse> objet;
    private List<NettoyageChoisieResponse> nettoyageChoisie;
}
