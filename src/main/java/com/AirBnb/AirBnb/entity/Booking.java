package com.AirBnb.AirBnb.entity;

import com.AirBnb.AirBnb.entity.enums.BookingStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;
@AllArgsConstructor
@Entity
@NoArgsConstructor
public class Booking {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="hotel_id", nullable = false)
    private Hotel hotel;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="room_id", nullable = false)
    private Room room;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id",nullable = false)
    private User user;

    @Column(nullable = false)
    private Integer roomCount;
    @Column(nullable=false)
    private LocalDate checkInDate;
    @Column(nullable = false)
    private LocalDate checkOutDate;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BookingStatus bookingStatus;

    @Column(nullable = false,precision = 10,scale = 2)
    private BigDecimal amount;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name="Booking_guest",
            joinColumns=@JoinColumn(name = "booking_id"),
            inverseJoinColumns = @JoinColumn(name="guest_id")
    )
    private Set<Guest> guests;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getRoomCount() {
        return roomCount;
    }

    public void setRoomCount(Integer roomCount) {
        this.roomCount = roomCount;
    }

    public LocalDate getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(LocalDate checkInDate) {
        this.checkInDate = checkInDate;
    }

    public LocalDate getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(LocalDate checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public BookingStatus getBookingStatus() {
        return bookingStatus;
    }

    public void setBookingStatus(BookingStatus bookingStatus) {
        this.bookingStatus = bookingStatus;
    }

    public Set<Guest> getGuests() {
        return guests;
    }

    public void setGuests(Set<Guest> guests) {
        this.guests = guests;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }


    private Booking(Builder builder)
        {
            this.id=builder.id;
            this.hotel= builder.hotel;
            this.room= builder.room;
            this.user= builder.user;
            this.roomCount=builder.roomCount;
            this.checkInDate=builder.checkInDate;
            this.checkOutDate=builder.checkOutDate;
            this.createdAt=builder.createdAt;
            this.updatedAt=builder.updatedAt;
            this.bookingStatus=builder.bookingStatus;
            this.amount=builder.amount;
        }
        public static Builder builder()
        {
            return new Builder();
        }

        public static class Builder
        {
            private Long id;
            private Hotel hotel;
            private Room room;
            private User user;
            private Integer roomCount;
            private LocalDate checkInDate;
            private LocalDate checkOutDate;
            private LocalDateTime createdAt;
            private LocalDateTime updatedAt;
            private BookingStatus bookingStatus;
            private BigDecimal amount;

            public Builder id(Long id) { this.id=id; return this;}
            public Builder hotel(Hotel hotel) {this.hotel=hotel; return this;}
            public Builder room(Room room) {this.room=room; return this;}
            public Builder user(User user) {this.user=user; return this;}
            public Builder roomCount(Integer roomCount) {this.roomCount=roomCount;return this;}
            public Builder checkInDate(LocalDate checkInDate) {this.checkInDate=checkInDate; return this;}
            public Builder checkOutDate(LocalDate checkOutDate) {this.checkOutDate=checkOutDate; return this;}
            public Builder createdAt(LocalDateTime createdAt) {this.createdAt=createdAt; return this;}
            public Builder updatedAt(LocalDateTime updatedAt) {this.updatedAt=updatedAt; return this;}
            public Builder bookingStatus(BookingStatus bookingStatus) {this.bookingStatus=bookingStatus; return this;}
            public Builder amount(BigDecimal amount) {this.amount=amount; return this;}

            public Booking build()
            {
                return new Booking( this);
            }
        }


}
