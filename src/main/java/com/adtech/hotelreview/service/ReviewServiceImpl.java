package com.adtech.hotelreview.service;

import com.adtech.hotelreview.config.Constants;
import com.adtech.hotelreview.config.Role;
import com.adtech.hotelreview.model.Hotel;
import com.adtech.hotelreview.model.Review;
import com.adtech.hotelreview.model.User;
import com.adtech.hotelreview.model.request.ReviewReq;
import com.adtech.hotelreview.model.request.UserAndReviewReq;
import com.adtech.hotelreview.model.request.UserReviewReq;
import com.adtech.hotelreview.repository.ReviewRepository;
import com.adtech.hotelreview.utils.Utilities;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class ReviewServiceImpl implements ReviewService{

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private HotelService hotelService;

    @Autowired
    private Utilities hotelUtils;

    @Override
    public List<Review> findAll() {
        return reviewRepository.findAll();
    }

    @Override
    public Review findById(Long id) {
        return reviewRepository.findById(id).get();
    }

    @Override
    public List<Review> findByUser(User user) {
        return reviewRepository.findByUser(user);
    }

    @Override
    public List<Review> findByHotel(Hotel hotel) {
        return reviewRepository.findByHotel(hotel);
    }

    public void createReview(UserReviewReq userReviewReq, Long userId){
        postBulkReview(userReviewReq.getReviewReqList(), userId);
    }

    @Override
    public Review postReview(UserAndReviewReq userAndReviewReq) {

        if (userAndReviewReq.getRating()==null || userAndReviewReq.getRating()<1 || userAndReviewReq.getRating()>5){
            log.error("Please provide ratings between 1 to 5");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Please provide ratings between 1 to 5");
        }

        if (userAndReviewReq.getUserId()==null && userAndReviewReq.getUserName()==null) {
            log.error("Please provide user information");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Please provide user information");
        }

        User user = userAndReviewReq.getUserName() != null ? userService.findByUsername(userAndReviewReq.getUserName()) : userService.findById(userAndReviewReq.getUserId());

        if (user == null) {
            log.error("Please provide valid user information");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Please provide valid user information");
        }

        if (userAndReviewReq.getHotelId()==null && userAndReviewReq.getHotelName()==null) {
            log.error("Please provide Hotel information");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Please provide Hotel information");
        }

        Hotel hotel = userAndReviewReq.getHotelName() != null ? hotelService.findByName(userAndReviewReq.getHotelName()) : hotelService.findById(userAndReviewReq.getHotelId());
        if (hotel == null) {
            log.error("Please provide valid Hotel information");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Please provide valid Hotel information");
        }

        List<Review> byUserAndHotel = reviewRepository.findByUserAndHotel(user, hotel);
        if (!byUserAndHotel.isEmpty()) {
            log.error("multiple reviews not allowed");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "multiple reviews not allowed");
        }

        Review review = new Review();
        review.setIsActive(true);
        review.setHotel(hotel);
        review.setUser(user);
        review.setComment(userAndReviewReq.getComment());
        review.setRating(calculateRatings(hotel, user, userAndReviewReq.getRating()));

        review = reviewRepository.save(review);
        return review;
    }

    private Integer calculateRatings(Hotel Hotel, User user, Integer ratings) {
        //can do user specific rating counts
        return ratings;
    }

    @Override
    @Transactional
    public void postBulkReview(List<ReviewReq> reviewReqs, Long userId) {
        List<Review> reviewList = new ArrayList<>();

        User user = userService.findById(userId);
        reviewReqs.forEach( x->
                {
                    Long hotelId = x.getHotelId();
                    Integer rating = x.getRating();
                    String comment = x.getComment();

                    Hotel hotel = hotelService.findById(hotelId);
                    if (hotel!=null){
                        Review review = new Review();
                        review.setIsActive(true);
                        review.setRating(hotelUtils.formatRatings(rating));
                        review.setComment(comment);
                        review.setHotel(hotel);
                        review.setUser(user);

                        save(review);
                        reviewList.add(review);
                    }
                }
        );
    }

    @Override
    public List<Review> findByUserId(Long userId) {
        User user = userService.findById(userId);
        return findByUser(user);
    }

    public Review save(Review review){
        review.setIsActive(review.getIsActive()==null ? true:review.getIsActive());
        return reviewRepository.save(review);
    }
    
    public Review disableReview(Review review,User user){
        boolean isAdmin = userService.isAdminUser(user);
        if (!isAdmin){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Only Admin Can disable Reviews");
        }

        review.setIsActive(false);
        return save(review);
    }

    @Override
    public List<Review> getAdminSpecific(Long userId){
        boolean isAdmin = userService.isAdminUser(userId);
        return isAdmin ? findAll(): findByUserId(userId);
    }
}
