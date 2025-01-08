package com.room.hotel.request;

import lombok.Data;

@Data
public class SalleChoisieRequest {
    private Long passeId;
    private String numero;
    private String statut;
}
