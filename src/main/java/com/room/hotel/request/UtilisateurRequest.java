package com.room.hotel.request;

import lombok.Data;

import java.util.UUID;

@Data
public class UtilisateurRequest {

    private UUID id;
    private String nom;
    private String password;
    private String email;
    private String role;
    private String photo;
    private Boolean connected;
}
