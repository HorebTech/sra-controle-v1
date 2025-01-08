package com.room.hotel.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PasswordRequest {
    @NotNull(message = "Nom requis!")
    private String nom;
    @NotNull(message = "Email requis!")
    private String email;
    private String newPassword;
}
