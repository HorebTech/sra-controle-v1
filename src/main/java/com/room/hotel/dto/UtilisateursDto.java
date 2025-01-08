package com.room.hotel.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class UtilisateursDto {
    private UUID id;
    private String nom;
    private String password;
    private String email;
    private String role;
    private String photo;
    private Boolean connected;
}
