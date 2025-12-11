package com.AirBnb.AirBnb.repository;

import com.AirBnb.AirBnb.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking,Long> {
}
