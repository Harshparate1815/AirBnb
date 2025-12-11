package com.AirBnb.AirBnb.service;

import com.AirBnb.AirBnb.dto.BookingDto;
import com.AirBnb.AirBnb.dto.BookingRequest;
import com.AirBnb.AirBnb.dto.GuestDto;

import java.util.List;

public interface BookingService {
    BookingDto initializeBooking(BookingRequest bookingRequest);

    BookingDto addGuest(Long bookingId, List<GuestDto> guestDtoList);
}
