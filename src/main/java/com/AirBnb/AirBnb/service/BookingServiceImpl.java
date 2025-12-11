package com.AirBnb.AirBnb.service;

import com.AirBnb.AirBnb.dto.BookingDto;
import com.AirBnb.AirBnb.dto.BookingRequest;
import com.AirBnb.AirBnb.dto.GuestDto;
import com.AirBnb.AirBnb.entity.*;
import com.AirBnb.AirBnb.entity.enums.BookingStatus;
import com.AirBnb.AirBnb.exception.ResourceNotFoundException;
import com.AirBnb.AirBnb.repository.*;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Slf4j
@Service
public class BookingServiceImpl implements BookingService{
    private final GuestRepository guestRepository;
    private final ModelMapper modelMapper;
    private final InventoryRepository inventoryRepository;

    private final BookingRepository bookingRepository;
    private final HotelRepository hotelRepository;
    private final RoomRepository roomRepository;

    public BookingServiceImpl(BookingRepository bookingRepository, HotelRepository hotelRepository, RoomRepository roomRepository,
                              InventoryRepository inventoryRepository,
                              ModelMapper modelMapper,
                              GuestRepository guestRepository) {
        this.bookingRepository = bookingRepository;
        this.hotelRepository = hotelRepository;
        this.roomRepository = roomRepository;
        this.inventoryRepository = inventoryRepository;
        this.modelMapper = modelMapper;
        this.guestRepository = guestRepository;
    }

    @Override
    @Transactional
    public BookingDto initializeBooking(BookingRequest bookingRequest) {
//        log.info("Initializing booking for hotel : {} ,room: {},date {}-{}",bookingRequest.getHotelId()
//                ,bookingRequest.getRoomId(),bookingRequest.getCheckInDate(),bookingRequest.getCheckOutDate());
        Hotel hotel=hotelRepository.findById(bookingRequest.getHotelId()).orElseThrow(()->
                new ResourceNotFoundException("Hotel not found with id:"+bookingRequest.getHotelId()));

        Room room=roomRepository.findById(bookingRequest.getRoomId()).orElseThrow(()->
                new ResourceNotFoundException("Room not found with id:"+bookingRequest.getRoomId()));

        List<Inventory> inventoryList=inventoryRepository.findAndLockAvailableInventory
                (room.getId(),
                bookingRequest.getCheckInDate(),
                bookingRequest.getCheckOutDate(),
                bookingRequest.getRoomsCount()
                );
        long daysCount= ChronoUnit.DAYS.between(bookingRequest.getCheckInDate(),bookingRequest.getCheckOutDate())+1;

        if(inventoryList.size()!=daysCount)
        {
            throw new IllegalStateException("Room is not available anymore ");
        }

        // Reserve the room/ update the booked count of inventories
        for(Inventory inventory : inventoryList)
        {
            inventory.setReservedCount(inventory.getReservedCount()+ bookingRequest.getRoomsCount());
        }
        inventoryRepository.saveAll(inventoryList);

        User user =new User();
        user.setId(1L);                 // TODO: Remove Dummy User

        // TODO: Calculate dynamic price

        // Create  the Booking
        Booking booking=Booking.builder()
                .bookingStatus(BookingStatus.RESERVED)
                .hotel(hotel)
                .room(room)
                .roomCount(bookingRequest.getRoomsCount())
                .checkInDate(bookingRequest.getCheckInDate())
                .checkOutDate(bookingRequest.getCheckOutDate())
                .user(getCurrentUser())
                .amount(BigDecimal.TEN)
                .build();
        booking=bookingRepository.save(booking);
        return modelMapper.map(booking, BookingDto.class);


    }

    @Override
    public BookingDto addGuest(Long bookingId, List<GuestDto> guestDtoList) {
//          log.info("Adding guest for booking with id: {}".bookingId);
                Booking booking=bookingRepository.findById(bookingId).orElseThrow(()->
                        new ResourceNotFoundException("Hotel not found with id:"+bookingId));

                if(hasBookingExpired(booking))
                {
                    throw new IllegalStateException("Booking has already expired");
                }

                if(booking.getBookingStatus() != BookingStatus.RESERVED)
                {
                    throw new IllegalStateException("Booking is not under reserved state, cannot add guests");
                }

                for(GuestDto guestDto:guestDtoList)
                {
                    Guest guest = modelMapper.map(guestDto, Guest.class);
                    guest.setUser(getCurrentUser());
                    guest=guestRepository.save(guest);
                    booking.getGuests().add(guest);
                }
                booking.setBookingStatus(BookingStatus.GUEST_ADDED);
                booking=bookingRepository.save(booking);
                return modelMapper.map(booking,BookingDto.class);
    }

    public boolean hasBookingExpired(Booking booking)
    {
        return booking.getCreatedAt().plusMinutes(10).isBefore(LocalDateTime.now());
    }

    public User getCurrentUser()
    {
        User user=new User();
        user.setId(1L);             // TODO:REMOVE DUMMY USER
        return user;
    }
}
