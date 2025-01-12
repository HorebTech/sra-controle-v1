package com.room.hotel.response;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PanneResponse {
    private String description;
    private String nomEquipement;
    private MarqueResponse marqueEquipement;
    private String photo;
    private String date;
    private StatutResponse statut;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy ' à ' HH:mm:ss")
    private LocalDateTime creationDate;
}
