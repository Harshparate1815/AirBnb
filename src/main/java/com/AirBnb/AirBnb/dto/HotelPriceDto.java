package com.AirBnb.AirBnb.dto;

import com.AirBnb.AirBnb.entity.Hotel;
import lombok.Data;

@Data

public class HotelPriceDto {
    private Hotel hotel;
    private Double price;

    public HotelPriceDto(Hotel hotel, Double price) {
        this.hotel = hotel;
        this.price = price;
    }

    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
