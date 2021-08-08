package com.adtech.hotelreview.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HotelDto {
    private String name;
    private String description;
    private Boolean isActive;
}
