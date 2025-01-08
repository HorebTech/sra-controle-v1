package com.room.hotel.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.room.hotel.response.ChambreChoisieResponse;
import com.room.hotel.response.SalleChoisieResponse;
import com.room.hotel.response.StatutResponse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ObjetDto {
    private UUID id;
    private String description;
    private String photo;
    private StatutResponse statut;
    private ChambreChoisieResponse chambreChoisie;
    private SalleChoisieResponse salleChoisie;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy ' à ' HH:mm:ss")
    private LocalDateTime creationDate;
}
