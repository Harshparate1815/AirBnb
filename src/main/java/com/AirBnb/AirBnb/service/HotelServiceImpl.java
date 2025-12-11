package com.AirBnb.AirBnb.service;

import com.AirBnb.AirBnb.dto.HotelDto;
import com.AirBnb.AirBnb.dto.HotelInfoDto;
import com.AirBnb.AirBnb.dto.RoomDto;
import com.AirBnb.AirBnb.entity.Hotel;
import com.AirBnb.AirBnb.entity.Room;
import com.AirBnb.AirBnb.exception.ResourceNotFoundException;
import com.AirBnb.AirBnb.repository.HotelRepository;
import com.AirBnb.AirBnb.repository.RoomRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
                                                                // Business Logic layer of Hotel and interact with Database : Executes core business logic and interact with Repository and entities
public class HotelServiceImpl implements HotelService{

    private final HotelRepository hotelRepository;
    private final ModelMapper modelMapper;
    private final InventoryService inventoryService;
    private final RoomRepository roomRepository;

    public HotelServiceImpl(HotelRepository hotelRepository, ModelMapper modelMapper, InventoryService inventoryService, RoomRepository roomRepository) {
        this.hotelRepository = hotelRepository;
        this.modelMapper = modelMapper;
        this.inventoryService = inventoryService;
        this.roomRepository = roomRepository;
    }

    // API 1 to create Hotel
    @Override
    public HotelDto createNewHotel(HotelDto hotelDto) {
        //log.info("Creating new Hotel with name : {}", hotelDto.getName());
        Hotel hotel=modelMapper.map(hotelDto,Hotel.class);
        hotel.setActive(false);
        hotel=hotelRepository.save(hotel);
        //log.info("Created new Hotel with ID : {}", hotelDto.getId());

        return modelMapper.map(hotel,HotelDto.class);
    }

    // API 2 to get Hotel ID
    @Override
    public HotelDto getHotelById(Long id) {
        //log.info("getting the Hotel with ID : {}", id);
        Hotel hotel = hotelRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Hotel not found with ID: "+id));
        return modelMapper.map(hotel,HotelDto.class);

    }

    // API 3 to modify Hotel

    @Override
    public HotelDto updateHotelByid(Long id, HotelDto hotelDto) {
        //log.info("Updating the hotel with ID:{}",id);
        Hotel hotel=hotelRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Hotel not found with ID:{}"+id));
        modelMapper.map(hotelDto,hotel);
        hotel.setId(id);
        hotel = hotelRepository.save(hotel);
        return modelMapper.map(hotel,HotelDto.class);
    }

    // API 4 to Delete

    @Override
    @Transactional                                                  // Transactional is used when we are doing two database call it this API deleteAllInventories and deleteById are two database calls
    public void deleteHotelById(Long id) {
        Hotel hotel=hotelRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Hotel not found with ID:"+id));

        for(Room room:hotel.getRooms())
        {
            inventoryService.deleteAllInventories(room);            // Deletes all the inventory related to roomID
            roomRepository.deleteById(room.getId());               // Deletes the room
        }
        hotelRepository.deleteById(id);
    }
        // API 5 Activate Hotel
    @Override
    @Transactional
    public void activateHotel(Long hotelId) {                                                   // If want to update little
       // log.info("Activating hotel with ID: {}",hotelId);
        Hotel hotel=hotelRepository
                .findById(hotelId)
                .orElseThrow(() -> new ResourceNotFoundException("Hotel not found with ID:"+hotelId));
        hotel.setActive(true);
        hotelRepository.save(hotel);
        // Assuming only do it once
        for(Room room: hotel.getRooms())
        {
            inventoryService.initializeRoomForAYear(room);
        }

    }
    //      API 6 Get Hotel Info

    @Override
    public HotelInfoDto getHotelInfoById(Long hotelId) {                // It will return the hotel as well as the room associated with it
        Hotel hotel=hotelRepository
                .findById(hotelId)
                .orElseThrow(()-> new ResourceNotFoundException("Hotel not found with ID:"+hotelId));

        List<RoomDto> rooms=hotel.getRooms()                            // returns the list of rooms associated with this hotel
                .stream()
                .map((element)->modelMapper.map(element,RoomDto.class)) // converts each room entity to RoomDto using ModelMapper
                .toList();                                              // collects the converted DTO object into a List<RoomDto>

        return new HotelInfoDto(modelMapper.map(hotel, HotelDto.class),rooms);
    }


}
