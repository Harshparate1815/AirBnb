package com.AirBnb.AirBnb.controller;

import com.AirBnb.AirBnb.dto.BookingDto;
import com.AirBnb.AirBnb.dto.BookingRequest;
import com.AirBnb.AirBnb.dto.GuestDto;
import com.AirBnb.AirBnb.service.BookingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bookings")
public class hotelBookingController {

    private final BookingService bookingService;

    public hotelBookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping("/init")
    public ResponseEntity<BookingDto> initializeBooking(@RequestBody BookingRequest bookingRequest)
    {
        return ResponseEntity.ok(bookingService.initializeBooking(bookingRequest));
    }
    @PostMapping("/{bookingId}/addGuests")
    public ResponseEntity<BookingDto> addGuests(@PathVariable Long bookingId, @RequestBody List<GuestDto> guestDtoList)
    {
        return ResponseEntity.ok(bookingService.addGuest(bookingId,guestDtoList));
    }
}
