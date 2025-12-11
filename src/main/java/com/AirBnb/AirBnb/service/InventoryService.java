package com.AirBnb.AirBnb.service;

import com.AirBnb.AirBnb.dto.HotelDto;
import com.AirBnb.AirBnb.dto.HotelSearchRequest;
import com.AirBnb.AirBnb.entity.Room;
import org.springframework.data.domain.Page;

public interface InventoryService {

    void initializeRoomForAYear(Room roomId);
    void deleteAllInventories(Room room);

    Page<HotelDto> searchHotels(HotelSearchRequest hotelSearchRequest);
}
