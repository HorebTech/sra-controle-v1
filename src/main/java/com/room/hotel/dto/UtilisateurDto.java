package com.room.hotel.dto;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UtilisateurDto {
    private UUID id;
    private String nom;
    private String password;
    private String email;
    private String role;
    private String photo;
    private Boolean connected;
}
