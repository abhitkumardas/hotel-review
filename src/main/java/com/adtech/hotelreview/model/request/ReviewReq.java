package com.adtech.hotelreview.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewReq {
    private Long hotelId;
    private Integer rating;
    private String comment;
}
