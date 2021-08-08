package com.adtech.hotelreview.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserAndReviewReq {
    private String userName;
    private Long userId;

    private Long hotelId;
    private String hotelName;
    private Integer rating;
    private String comment;
}
