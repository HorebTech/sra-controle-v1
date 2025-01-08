package com.room.hotel.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.room.hotel.response.ChambreChoisieResponse;
import com.room.hotel.response.SalleChoisieResponse;
import com.room.hotel.response.StatutResponse;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PanneDto {
    private UUID id;
    private String description;
    private String nomEquipement;
    private String marqueEquipement;
    private ChambreChoisieResponse chambreChoisie;
    private SalleChoisieResponse salleChoisie;
    private String photo;
    private String date;
    private StatutResponse statut;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy ' à ' HH:mm:ss")
    private LocalDateTime creationDate;
}
