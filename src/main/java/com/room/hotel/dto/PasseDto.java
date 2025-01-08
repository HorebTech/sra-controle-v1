package com.room.hotel.dto;
import java.util.List;

import com.room.hotel.response.ChambreChoisieResponse;
import com.room.hotel.response.NettoyageChoisieResponse;
import com.room.hotel.response.SalleChoisieResponse;
import com.room.hotel.response.StatutResponse;
import com.room.hotel.response.TacheChoisieResponse;
import com.room.hotel.response.UtilisateurResponse;

import lombok.Data;

@Data
public class PasseDto {
    private Long id;
    private UtilisateurResponse agent;
    private UtilisateurResponse gouvernante;
    private StatutResponse statut;
    private String commentaire;
    private String dateNettoyage;
    private List<NettoyageChoisieResponse> nettoyageChoisie;
    private List<TacheChoisieResponse> tacheChoisie;
    private List<ChambreChoisieResponse> chambreChoisie;
    private List<SalleChoisieResponse> salleChoisie;
}
