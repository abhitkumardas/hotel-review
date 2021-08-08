package com.adtech.hotelreview.controller;

import com.adtech.hotelreview.model.Hotel;
import com.adtech.hotelreview.model.request.HotelDto;
import com.adtech.hotelreview.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/hotel")
public class HotelController {

    @Autowired
    private HotelService hotelService;


    @PostMapping
    public ResponseEntity addHotel(@RequestBody HotelDto hotelDto){
        return ResponseEntity.ok(hotelService.addHotel(hotelDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity getAll(@PathVariable Long id){
        return ResponseEntity.ok(hotelService.findById(id));
    }

    @GetMapping("/all")
    public ResponseEntity getAll(){
        return ResponseEntity.ok(hotelService.findAll());
    }




    @PostMapping(value = "/admin")
    public ResponseEntity addHotelAdmin(@RequestBody Hotel Hotel, @RequestHeader Long userId){
        return ResponseEntity.ok(hotelService.addHotelAdmin(Hotel, userId));
    }

    @PutMapping
    public ResponseEntity updateHotel(@RequestBody Hotel Hotel, @RequestHeader Long userId){
        return ResponseEntity.ok(hotelService.updateHotel(Hotel, userId));
    }
}
