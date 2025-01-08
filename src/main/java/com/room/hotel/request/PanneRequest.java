package com.room.hotel.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class PanneRequest {
    private String id;
    private String description;
    private String nomEquipement;
    private String marqueEquipement;
    private String imageBase64;
    private String date;
    private String statut;
}
