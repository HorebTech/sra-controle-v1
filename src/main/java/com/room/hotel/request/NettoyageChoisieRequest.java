package com.room.hotel.request;

import lombok.Data;

@Data
public class NettoyageChoisieRequest {
    private Long passeId;
    private String action;
}
