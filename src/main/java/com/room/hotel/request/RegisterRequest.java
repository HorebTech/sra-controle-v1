package com.room.hotel.request;


import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {
    @NotNull(message = "email requis!")
    private String email;
    @NotNull(message = "Nom requis!")
    private String nom;
    private String password;
    private String role;
}