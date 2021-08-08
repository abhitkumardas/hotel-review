package com.adtech.hotelreview.repository;

import com.adtech.hotelreview.model.Hotel;
import com.adtech.hotelreview.model.Review;
import com.adtech.hotelreview.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    public List<Review> findAll();
    public Optional<Review> findById(Long id);
    public List<Review> findByUser(User user);
    public List<Review> findByHotel(Hotel Hotel);
    public List<Review> findByUserAndHotel(User user, Hotel Hotel);
}
