package com.AirBnb.AirBnb.service;

import com.AirBnb.AirBnb.dto.RoomDto;
import com.AirBnb.AirBnb.entity.Hotel;
import com.AirBnb.AirBnb.entity.Room;
import com.AirBnb.AirBnb.exception.ResourceNotFoundException;
import com.AirBnb.AirBnb.repository.HotelRepository;
import com.AirBnb.AirBnb.repository.InventoryRepository;
import com.AirBnb.AirBnb.repository.RoomRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class RoomServiceImpl implements RoomService{
    private final InventoryRepository inventoryRepository;
    private final RoomRepository roomRepository;
    private final HotelRepository hotelRepository;
    private final ModelMapper modelMapper;
    private final InventoryService inventoryService;

    public RoomServiceImpl(RoomRepository roomRepository, HotelRepository hotelRepository, ModelMapper modelMapper,
                           InventoryRepository inventoryRepository, InventoryService inventoryService) {
        this.roomRepository = roomRepository;
        this.hotelRepository = hotelRepository;
        this.modelMapper = modelMapper;
        this.inventoryRepository = inventoryRepository;
        this.inventoryService = inventoryService;
    }

    @Override
    public RoomDto createNewRoom(Long hotelId, RoomDto roomDto) {
        //log.info("Creating new Room with name :{}",hotelId);
        Hotel hotel=hotelRepository
                .findById(hotelId)
                .orElseThrow(() -> new ResourceNotFoundException("Hotel not found with ID:{}"+hotelId));
        Room room=modelMapper.map(roomDto,Room.class);
        room.setHotel(hotel);
        room= roomRepository.save(room);
        if(hotel.getActive())                   // Only when hotel is active
        {
            inventoryService.initializeRoomForAYear(room);
        }
        return modelMapper.map(room, RoomDto.class);
    }

    @Override
    public List<RoomDto> getAllRoomsInHotel(Long hotelId) {
        //log.info("Getting all rooms with ID :{}",hotelId);
        Hotel hotel=hotelRepository
                .findById(hotelId)
                .orElseThrow(() -> new ResourceNotFoundException("Hotel not found with ID:{}"+hotelId));
        return hotel.getRooms()
                .stream()
                .map((element) -> modelMapper.map(element, RoomDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public RoomDto getRoomById(Long roomId) {
        //log.info("getting the Room with ID :{}",roomId);
        Room room=roomRepository
                .findById(roomId)
                .orElseThrow(() -> new ResourceNotFoundException("Room not found with ID:{}"+roomId));
        return modelMapper.map(room,RoomDto.class);
    }

    @Transactional
    @Override
    public void deleteRoomByid(Long roomId) {
        //log.info("Deleting the Room with ID :{}",roomId);
        Room room=roomRepository
                .findById(roomId)
                .orElseThrow(() -> new ResourceNotFoundException("Room not found with ID:{}"+roomId));

       inventoryService.deleteAllInventories(room);
       roomRepository.deleteById(roomId);
    }
}
