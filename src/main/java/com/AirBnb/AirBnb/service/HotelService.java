package com.AirBnb.AirBnb.service;

import com.AirBnb.AirBnb.dto.HotelDto;
import com.AirBnb.AirBnb.dto.HotelInfoDto;

public interface HotelService {

    // All API
    HotelDto createNewHotel(HotelDto hotelDto);
    HotelDto getHotelById(Long id);
    HotelDto updateHotelByid(Long id,HotelDto hotelDto);

    void deleteHotelById(Long id);

    void activateHotel(Long hotelId);

    HotelInfoDto getHotelInfoById(Long hotelId);
}
