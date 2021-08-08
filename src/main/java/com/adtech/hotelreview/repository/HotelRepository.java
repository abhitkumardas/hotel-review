package com.adtech.hotelreview.repository;

import com.adtech.hotelreview.model.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, Long> {

    public List<Hotel> findAll();

    public Optional<Hotel> findById(Long userId);

    public Hotel findByName(String name);
}
