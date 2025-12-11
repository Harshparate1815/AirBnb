package com.AirBnb.AirBnb.repository;

import com.AirBnb.AirBnb.entity.Hotel;
import com.AirBnb.AirBnb.entity.Inventory;
import com.AirBnb.AirBnb.entity.Room;
import jakarta.persistence.LockModeType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface InventoryRepository extends JpaRepository<Inventory,Long> {
    void deleteByRoom(Room room);
    @Query("""
            SELECT DISTINCT i.hotel
            FROM Inventory i
            WHERE i.city= :city
                AND i.date BETWEEN :startDate AND :endDate
                AND i.closed=false
                AND (i.totalCount - i.bookedCount-i.reservedCount) >= :roomsCount
            GROUP BY i.hotel,i.room
            HAVING COUNT(i.date) = :dateCount
            """)
    Page<Hotel>  findHotelsWithAvailableInventory(
            @Param("city") String city,
            @Param("startDate")LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            @Param("roomsCount") Integer roomCount,
            @Param("dateCount") Long dateCount,
            Pageable pageable
            );

    // This method is used when someone tries to book a room
    // It checks if there is available inventory and locks it so that no one else can book it at the same time
    @Query("""
            SELECT i
            FROM Inventory i
            WHERE i.room.id = :roomId                                               
                AND i.date BETWEEN :startDate AND :endDate
                AND i.closed = false                                                
                AND (i.totalCount - i.bookedCount - i.reservedCount) >= :roomsCount
            """)
    @Lock(LockModeType.PESSIMISTIC_WRITE)

    List<Inventory> findAndLockAvailableInventory(
       @Param("roomId") Long roomId,
       @Param("startDate") LocalDate startDate,
       @Param("endDate") LocalDate endDate,
       @Param("roomsCount") Integer roomsCount
    );
}
