package com.AirBnb.AirBnb.controller;

import com.AirBnb.AirBnb.dto.HotelDto;
import com.AirBnb.AirBnb.service.HotelService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/hotels")
@Slf4j
                                                    // API layer : Handles the incoming Http requests and maps to service methods and return response
public class HotelController {
    private final HotelService hotelService;

    public HotelController(HotelService hotelService) {
        this.hotelService = hotelService;
    }

    @PostMapping
    public ResponseEntity<HotelDto> createNewHotel(@RequestBody HotelDto hotelDto)
    {
        //log.info("Attempting to create a new hotel with name :"+hotelDto.getName());
        HotelDto hotel=hotelService.createNewHotel(hotelDto);
        return new ResponseEntity<>(hotel, HttpStatus.CREATED);
    }

    @GetMapping("{hotelId}")
    public ResponseEntity<HotelDto> getHotelById(@PathVariable Long hotelId)
    {
        HotelDto hotelDto=hotelService.getHotelById(hotelId);
        return ResponseEntity.ok(hotelDto);
    }

    @PutMapping("/{hotelId}")
    public ResponseEntity<HotelDto> UpdateHotelById(@PathVariable Long hotelId,@RequestBody HotelDto hotelDto)
    {
        HotelDto hotel=hotelService.updateHotelByid(hotelId,hotelDto);
        return ResponseEntity.ok(hotel);
    }

    @DeleteMapping("/{hotelId}")
    public ResponseEntity<Void> deleteHotelById(@PathVariable Long hotelId)
    {
        hotelService.deleteHotelById(hotelId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{hotelId}/activate")
    public ResponseEntity<Void> activateHotel(@PathVariable Long hotelId)
    {
        hotelService.activateHotel(hotelId);
        return ResponseEntity.noContent().build();
    }


}
