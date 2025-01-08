package com.room.hotel.utils;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.room.hotel.model.Utilisateur;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AuthResponse {
    private int statusCode;
    private UUID id;
    private String error;
    private String message;
    private String accessToken;
    private String refreshToken;
    private String expirationTime;
    private String email;
    private String photo;
    private String role;
    private String nom;
    private Boolean connected;
    private Utilisateur utilisateur;
}
