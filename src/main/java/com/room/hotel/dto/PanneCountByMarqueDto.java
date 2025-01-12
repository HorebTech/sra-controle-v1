package com.room.hotel.dto;

import lombok.Data;

@Data
public class PanneCountByMarqueDto {
    private String marque;
    private Long total;

    public PanneCountByMarqueDto(String marque, Long total) {
        this.marque = marque;
        this.total = total;
    }

    // Getters et setters
    public String getMarque() {
        return marque;
    }

    public void setMarque(String marque) {
        this.marque = marque;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }
}
