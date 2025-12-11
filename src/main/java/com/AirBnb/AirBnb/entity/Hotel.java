package com.AirBnb.AirBnb.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name="hotel")
public class Hotel
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)                       // If we don't specify then error will occur
    private String name;
    private String city;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(columnDefinition = "TEXT[]")
    private String[] photos;
    @Column(columnDefinition = "TEXT[]")
    private String[] amenities;
    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;
    @Embedded
    private HotelContactInfo contactInfo;
    @Column(nullable=false)
    private boolean active;

    public boolean getActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String[] getPhotos() {
        return photos;
    }

    public void setPhotos(String[] photos) {
        this.photos = photos;
    }

    public String[] getAmenities() {
        return amenities;
    }

    public void setAmenities(String[] amenities) {
        this.amenities = amenities;
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

    public HotelContactInfo getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(HotelContactInfo contactInfo) {
        this.contactInfo = contactInfo;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }
    //    @OneToMany(mappedBy = "hotel",fetch = FetchType.LAZY)
//    private List<Room> rooms;

    @ManyToOne
    private User owner;

    @OneToMany(mappedBy = "hotel")
    private List<Room> rooms;

    public List<Room> getRooms() {
        return rooms;
    }

    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }
}

