package com.AirBnb.AirBnb.dto;

import com.AirBnb.AirBnb.entity.User;
import com.AirBnb.AirBnb.entity.enums.Gender;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class GuestDto {

    private Long id;

    private User user;
    private String name;
    private LocalDateTime createdAt;
    private Gender gender;
    private Integer age;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
