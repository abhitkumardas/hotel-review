package com.adtech.hotelreview.service;



import com.adtech.hotelreview.model.Hotel;
import com.adtech.hotelreview.model.Review;
import com.adtech.hotelreview.model.User;
import com.adtech.hotelreview.model.request.ReviewReq;
import com.adtech.hotelreview.model.request.UserAndReviewReq;
import com.adtech.hotelreview.model.request.UserReviewReq;

import java.util.List;

public interface ReviewService {
    public List<Review> findAll();
    public Review findById(Long id);
    public List<Review> findByUser(User user);
    public List<Review> findByHotel(Hotel hotel);

    public void createReview(UserReviewReq userReviewReq, Long userId);

    public Review postReview(UserAndReviewReq userAndReviewReq);

    public void postBulkReview(List<ReviewReq> reviewReqs, Long userId);

    public List<Review> findByUserId(Long userId);

    public List<Review> getAdminSpecific(Long userId);
}
