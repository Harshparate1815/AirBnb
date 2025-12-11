package com.AirBnb.AirBnb.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
@Entity
@Table(
        uniqueConstraints=@UniqueConstraint(
        name="unique_hotel_room.date",
        columnNames={"hotel_id","room_id","date"}

))
public class Inventory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch=FetchType.LAZY )                  // Whenever we are fetching the inventory of particular id then we will fetch the Hotel information
    @JoinColumn(name="hotel_id",nullable = false)       //
    private Hotel hotel;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="room_id",nullable = false)
    private Room room;

    @Column(nullable=false)
    private LocalDate date;
    @Column(nullable=false,columnDefinition = "INTEGER DEFAULT 0")      // Define the default of bookedCount=0
    private Integer bookedCount;

    @Column(nullable=false,columnDefinition = "INTEGER DEFAULT 0")
    private Integer reservedCount;
    @Column(nullable = false)
    private Integer totalCount;
    @Column(nullable = false,precision = 5 , scale = 2)
    private BigDecimal surgeFactor;

    @Column(nullable = false, precision = 10,scale = 2)
    private BigDecimal price;       //basePrice = surgeFactor

    @Column(nullable = false)
    private String city;
    @Column(nullable = false)
    private Boolean closed;

    @CreationTimestamp
    private LocalDateTime createdAt;
    public Inventory()
    {

    }

    public Inventory(Long id, Hotel hotel, Room room, LocalDate date, Integer bookedCount, Integer reservedCount, Integer totalCount, BigDecimal surgeFactor, BigDecimal price, String city, Boolean closed, LocalDateTime createdAt) {
        this.id = id;
        this.hotel = hotel;
        this.room = room;
        this.date = date;
        this.bookedCount = bookedCount;
        this.reservedCount = reservedCount;
        this.totalCount = totalCount;
        this.surgeFactor = surgeFactor;
        this.price = price;
        this.city = city;
        this.closed = closed;
        this.createdAt = createdAt;
    }

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

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Integer getBookedCount() {
        return bookedCount;
    }

    public void setBookedCount(Integer bookedCount) {
        this.bookedCount = bookedCount;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public BigDecimal getSurgeFactor() {
        return surgeFactor;
    }

    public void setSurgeFactor(BigDecimal surgeFactor) {
        this.surgeFactor = surgeFactor;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getCity() {
        return city;
    }

    public Integer getReservedCount() {
        return reservedCount;
    }

    public void setReservedCount(Integer reservedCount) {
        this.reservedCount = reservedCount;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Boolean getClosed() {
        return closed;
    }

    public void setClosed(Boolean closed) {
        this.closed = closed;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    private Inventory(Builder builder) {
        this.id = builder.id;
        this.hotel = builder.hotel;
        this.room = builder.room;
        this.date = builder.date;
        this.bookedCount = builder.bookedCount;
        this.reservedCount=builder.reservedCount;
        this.totalCount = builder.totalCount;
        this.surgeFactor = builder.surgeFactor;
        this.price = builder.price;
        this.city = builder.city;
        this.closed = builder.closed;
        this.createdAt = builder.createdAt;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Long id;
        private Hotel hotel;
        private Room room;
        private LocalDate date;
        private Integer bookedCount;
        private Integer totalCount;
        private BigDecimal surgeFactor;
        private BigDecimal price;
        private String city;
        private Boolean closed;
        private LocalDateTime createdAt;
        private Integer reservedCount;

        public Builder id(Long id) { this.id = id; return this; }
        public Builder hotel(Hotel hotel) { this.hotel = hotel; return this; }
        public Builder room(Room room) { this.room = room; return this; }
        public Builder date(LocalDate date) { this.date = date; return this; }
        public Builder bookedCount(Integer bookedCount) { this.bookedCount = bookedCount; return this; }
        public Builder totalCount(Integer totalCount) { this.totalCount = totalCount; return this; }
        public Builder surgeFactor(BigDecimal surgeFactor) { this.surgeFactor = surgeFactor; return this; }
        public Builder price(BigDecimal price) { this.price = price; return this; }
        public Builder city(String city) { this.city = city; return this; }
        public Builder closed(Boolean closed) { this.closed = closed; return this; }
        public Builder createdAt(LocalDateTime createdAt) { this.createdAt = createdAt; return this; }
        public Builder reservedCount(Integer reservedCount) { this.reservedCount=reservedCount; return this;}

        public Inventory build() {
            return new Inventory(this);
        }
    }

}
