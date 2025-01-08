package com.room.hotel.request;

import lombok.Data;

@Data
public class ChambreChoisieRequest {
    private Long passeId;
    private String numero;
    private String statut;
}
