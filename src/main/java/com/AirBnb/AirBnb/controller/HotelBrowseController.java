package com.AirBnb.AirBnb.controller;

import com.AirBnb.AirBnb.dto.HotelDto;
import com.AirBnb.AirBnb.dto.HotelInfoDto;
import com.AirBnb.AirBnb.dto.HotelSearchRequest;
import com.AirBnb.AirBnb.service.HotelService;
import com.AirBnb.AirBnb.service.InventoryService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/hotels")
public class HotelBrowseController {
    private final InventoryService inventoryService;
    private final HotelService hotelService;
    public HotelBrowseController(InventoryService inventoryService, HotelService hotelService) {
        this.inventoryService = inventoryService;
        this.hotelService = hotelService;
    }

    @GetMapping("/search")
    public ResponseEntity<Page<HotelDto>> searchHotels(@RequestBody HotelSearchRequest hotelSearchRequest)
    {

        Page<HotelDto> page=inventoryService.searchHotels(hotelSearchRequest);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{hotelId}/info")
    public ResponseEntity<HotelInfoDto> getHotelInfo(@PathVariable Long hotelId)
    {
        return ResponseEntity.ok(hotelService.getHotelInfoById(hotelId));
    }
}
