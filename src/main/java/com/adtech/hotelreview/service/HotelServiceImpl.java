package com.adtech.hotelreview.service;


import com.adtech.hotelreview.exception.CustomRuntimeException;
import com.adtech.hotelreview.model.Hotel;
import com.adtech.hotelreview.model.request.HotelDto;
import com.adtech.hotelreview.repository.HotelRepository;
import com.adtech.hotelreview.utils.Utilities;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class HotelServiceImpl implements HotelService {

    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private Utilities utilities;

    @Override
    public Hotel findById(Long id) {
        return hotelRepository.findById(id).get();
    }

    @Override
    public Hotel findByName(String name) {
        return hotelRepository.findByName(name);
    }

    @Override
    public List<Hotel> findAll() {
        return hotelRepository.findAll();
    }

    @Override
    public Hotel addHotel(HotelDto hotelDto) {
        if (hotelDto.getName()==null || hotelDto.getName().isEmpty()){
            log.error("please provide Hotel name");
            throw new CustomRuntimeException("Please provide Hotel name");
        }
        Hotel existingHotel = hotelRepository.findByName(hotelDto.getName());
        if (existingHotel != null) {
            log.error("Hotel Exists with Name: " + hotelDto.getName());
            throw new CustomRuntimeException("Hotel Exists with Name: " + hotelDto.getName());
        }
        Hotel hotel = new Hotel();
        hotel.setName(hotelDto.getName());
        hotel.setIsActive(hotelDto.getIsActive()!=null ? hotelDto.getIsActive():true);
        hotel.setDescription(hotelDto.getDescription());

        return save(hotel);
    }

    //if needed feature admin can only add hotels
    @Override
    public Hotel addHotelAdmin(Hotel hotel, Long userId) {
        boolean isAdmin = userService.isAdminUser(userId);
        // As we have not used any Authentication and Authorization System using roles to authorize Admin Permission.

        if (!isAdmin){
            new RuntimeException("Only Admin Can add Hotel");
        }

        return save(hotel);
    }

    @Override
    public Hotel updateHotel(Hotel hotel, Long userId) {
        boolean isAdmin = userService.isAdminUser(userId);
        if (!isAdmin){
            throw new RuntimeException("Only Admin Can add Hotel");
        }

        return save(hotel);
    }

    public Hotel save(Hotel hotel) {
        hotel.setIsActive(hotel.getIsActive()==null ? true:hotel.getIsActive());
        return hotelRepository.save(hotel);
    }


}
