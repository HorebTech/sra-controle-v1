package com.room.hotel.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ObjetRequest {
    private String id;
    private String description;
    private String imageBase64;
    private String statut;
}
