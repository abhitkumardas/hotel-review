package com.adtech.hotelreview.service;


import com.adtech.hotelreview.model.Hotel;
import com.adtech.hotelreview.model.request.HotelDto;

import java.util.List;

public interface HotelService {
    public Hotel findById(Long id);

    public Hotel findByName(String name);

    public List<Hotel> findAll();

    public Hotel addHotel(HotelDto hotelDto);

    public Hotel addHotelAdmin(Hotel Hotel, Long userId);

    public Hotel updateHotel(Hotel Hotel, Long userId);
}
