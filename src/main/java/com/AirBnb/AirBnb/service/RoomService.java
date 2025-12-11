package com.AirBnb.AirBnb.service;

import com.AirBnb.AirBnb.dto.RoomDto;

import java.util.List;

public interface RoomService{
    // All API for Room
RoomDto createNewRoom(Long hotelId,RoomDto roomDto);
List<RoomDto> getAllRoomsInHotel(Long hotelId);
RoomDto getRoomById(Long roomId);
void deleteRoomByid(Long roomId);

}
