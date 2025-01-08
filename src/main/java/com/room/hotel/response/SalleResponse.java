package com.room.hotel.response;

import lombok.Data;

@Data
public class SalleResponse {
    private String numero;
    private String description;
    private StatutResponse statut;
}
